package Java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.base.BaseDateTime;

/**
 * This class represents all actions of the testing center. Interactions with the database will be made
 * in this class. Information about the testing center is also stored here. An internal class Notify
 * will be a separate thread to trigger send Notifications.
 */

/**
 * @author WdNnD
 * This class is a Singleton
 */
public class TestingCenter {
	
	private static final Logger logger = Logger.getLogger(TestingCenter.class.getName());
	
	private static TestingCenter instance = null;
	
	private static final int DEFAULT_SEATS = 64;
	private static final int DEFAULT_SET_ASIDE = 0;
	private static final LocalTime DEFAULT_OPEN = new LocalTime(8,0);
	private static final LocalTime DEFAULT_CLOSE = new LocalTime(8,0);
	private static final Period DEFAULT_GAP = new Period(1,0,0,0);
	private static final Period DEFAULT_REMINDER_INTERVAL = new Period(1,0,0,0);
	
	private List<Day> days;
	private int numberOfSeats;
	private int numberOfSetAside;
	private LocalTime open;
	private LocalTime close;
	private Period gap;
	private Period reminderInt;
	private Database db;
	private Notifier notifier;
	
	/**
	 * @param db 
	 * 
	 */

	public TestingCenter() {
		this(new ArrayList<Day>(), DEFAULT_SEATS, DEFAULT_SET_ASIDE,
				DEFAULT_OPEN, DEFAULT_CLOSE, DEFAULT_GAP, 
				DEFAULT_REMINDER_INTERVAL);
	}
	
	public TestingCenter(List<Day> days, int numberOfSeats, int numberOfSetAside, LocalTime open, LocalTime close,
			Period gap, Period reminderInt) {
		super();
		this.days = days;
		this.numberOfSeats = numberOfSeats;
		this.numberOfSetAside = numberOfSetAside;
		this.open = open;
		this.close = close;
		this.gap = gap;
		this.reminderInt = reminderInt;
		this.db = Database.getDatabase();
		notifier = new Notifier("thread");
	}

	public static TestingCenter getTestingCenter() {
		if (instance == null) {
			
			instance = new TestingCenter();
			
			logger.info("Instantiating testing center.");
			logger.fine("Days open: " + instance.days);
			logger.fine("Number of seats: " + instance.numberOfSeats);
			logger.fine("Opening time: " + instance.open.toString());
			logger.fine("Close time: " + instance.close.toString());
			logger.fine("Gap time: " + instance.gap.toString());
			logger.fine("Reminder interval: " + instance.reminderInt.toString());
		}
		return instance;
	}
	
	public static TestingCenter getTestingCenter(List<Day> days, int numberOfSeats, int numberOfSetAside, LocalTime open, LocalTime close,
			Period gap, Period reminderInt) {
		if (instance == null) {
			instance = new TestingCenter(days, numberOfSeats, numberOfSetAside,
					open, close, gap, reminderInt
					);
			
			logger.info("Instantiating testing center.");
			logger.fine("Days open: " + instance.days);
			logger.fine("Number of seats: " + instance.numberOfSeats);
			logger.fine("Opening time: " + instance.open.toString());
			logger.fine("Close time: " + instance.close.toString());
			logger.fine("Gap time: " + instance.gap.toString());
			logger.fine("Reminder interval: " + instance.reminderInt.toString());
		}
		return instance;
	}
	/*
	 * (NOTE: Many of these functions have been implemented but their functionality are in a different class 
	 * for the moment. For the next submission the project will be organized correctly or the design will be
	 * changed. For Example- makeAppointment is currently being done in Student but at this time it does not
	 * use checkAvailability().) 
	 */
	
	public synchronized List<DateTime> getAvailabile(Exam exam, String netId) {
		List<DateTime> slots = new ArrayList<DateTime>();
		DateTime tStart = exam.getStart();
		DateTime tEnd = exam.getEnd();
		long start = tStart.getMillis()/1000;
		long end = tEnd.getMillis()/1000;

		long gapTime = (long)gap.getMinutes()*60;
		long actualLen = gapTime+exam.getLength();  
		long rem = actualLen%1800;
		long len =0;
		if(rem == 0) {
			len = actualLen;
		} else {
			len = actualLen/1800+1;
			len = len*1800;
		}
		
		long search = start;
		
		for(long l = start;l<(end-len); l = l+1800){
			boolean coexist = true;
			while(coexist) {
				List<Map<String,Object>> apps = db.query(String.format("SELECT end FROM appointment"
						+ "WHERE studentIdA = '%s' AND startTime = '%d'",
						netId, search));
				if(apps.get(0) == null) {
					coexist = false;
				} else {
					search = (long) apps.get(0).get("end");
				}
			}
			for(int i = 0;i<numberOfSeats-numberOfSetAside;i++) {
				boolean clear = true;
				for(l = search; l<search+len && clear; l=l+1800) {
					List<Map<String,Object>> apps = db.query(String.format("SELECT examIdT FROM timeSlots"
							+ "WHERE dateId = '%d' AND seatId = '%d'",
							l,i));
					if(apps.get(0)!= null) {
						clear = false;
					} else {
						apps = db.query(String.format("SELECT seatId FROM timeSlots"
								+ "WHERE dateId = '%d' AND examIdT = '%s'",
								l,exam.getExamID()));
						if(apps.contains((i-1))|| apps.contains((i+1))) {
							clear = false;
						}
					}
				}
				if(clear) {
					DateTime possible = new DateTime(search*1000);
					slots.add(possible);
				}
			}
		}
			
		
		return slots;
		
	}
	
	//make an appointment to take an exam
	//
	public synchronized boolean makeAppointment(Exam exam, DateTime time, int appointmentId, String netID, DateTime startTime, DateTime endTime) throws ExistingAppointmentException {
		logger.info("Creating new Appointment");
		logger.fine("Exam id: " + exam.getExamID());
		logger.fine("Student ID: " + netID);
		logger.fine("Appointment start time: " + time.toString());
		logger.fine("Appointment ID: " + appointmentId);
		
		if (hasAppointment(netID, exam.getExamID())) {
			logger.warning("Student " + netID + " already has appointment for exam " + exam.getExamID());
			//throw new ExistingAppointmentException("Student " + netID + " already has appointment for exam " + exam.getExamID());
			return false;
		}
		
		if (conflictingAppointment(netID, startTime, endTime)) {
			logger.warning("Student " + netID + " already has an appointment between " + startTime + " and " + endTime);
			//throw new ConflictingAppointmentException("Student " + netID + " already has an appointment between " + startTime + " and " + endTime);
			return false;
		}
		
		if (appointmentOutOfExamBounds(exam.getExamID(), startTime, endTime)) {
			logger.warning("Requested appointment not within bounds of exam " + exam.getExamID() + " start and end times.");
			//throw new OutOfExamBoundsException("Requested appointment not within bounds of exam " + exam.getExamID() + " start and end times.");
			return false;
		}
		

		boolean avalSeat = false;
		long start = startTime.getMillis()/1000;
		long gapTime = (long)gap.getMinutes()*60;
		long actualLen = gapTime+exam.getLength();  
		long rem = actualLen%1800;
		long len =0;
		if(rem == 0) {
			len = actualLen;
		} else {
			len = actualLen/1800+1;
			len = len*1800;
		}
		for(int i = 0;i<numberOfSeats-numberOfSetAside && !avalSeat;i++) {
			boolean clear = true;
			for(long l = start; l<start+len && clear; l=l+1800) {
				List<Map<String,Object>> apps = db.query(String.format("SELECT examIdT FROM timeSlots"
						+ "WHERE dateId = '%d' AND seatId = '%d'",
						l,i));
				if(apps.get(0)!= null) {
					clear = false;
				} else {
					apps = db.query(String.format("SELECT seatId FROM timeSlots"
							+ "WHERE dateId = '%d' AND examIdT = '%s'",
							l,exam.getExamID()));
					if(apps.contains((i-1))|| apps.contains((i+1))) {
						clear = false;
					}
				}
				if(clear) {
					avalSeat = true;
					for(l = start; l<start+len; l=l+1800) {
						db.updateQuery(String.format("INSERT INTO timeslots VALUES ("
								+"'%d','%d','%s','%s'", 
								start,
								i,
								netID,
								exam.getExamID()));
					}
					String queryString = String.format("INSERT INTO appointment VALUES ("
							+ "'%s', '%s', %d, %d, %d, %d, %d)", 
							exam.getExamID(), 
							netID, 
							time.getMillis()/1000,
							i,
							appointmentId,
							startTime.getMillis()/1000,
							endTime.getMillis()/1000
							);
					db.updateQuery(queryString);
				}
			}
		}
		if(!avalSeat) {
			return false;
		}
		return true;
	}
	
	private boolean appointmentOutOfExamBounds(String examID, DateTime startTime, DateTime endTime) {
		String queryString = String.format("SELECT start, end "
				+ "FROM exam "
				+ "WHERE examId = '%s'",
				examID);
		List<Map<String, Object>> exams = Database.getDatabase().query(queryString);
		
		// We assume that exactly one exam exists with id examID
		Map<String, Object> exam = exams.get(0);
		
		return ((long) exam.get("start") > endTime.getMillis()/1000) || ((long) exam.get("end") < startTime.getMillis()/1000); 
	}

	private boolean conflictingAppointment(String netID, DateTime startTime, DateTime endTime) {
		String queryString = String.format("SELECT appointmentId "
				+ "FROM appointment "
				+ "WHERE studentIdA = '%s' "
				+ "AND "
				+ "(startTime <= '%s' "
				+ "AND endTime >= '%s')", 
				netID,
				endTime.getMillis()/1000 + gap.getMillis()/1000,
				startTime.getMillis()/1000 - gap.getMillis()/1000);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		return appointments.size() > 0; 
	}

	private boolean hasAppointment(String netID, String examID) {
		String queryString = String.format("SELECT appointmentId "
				+ "FROM appointment "
				+ "WHERE examIdA = '%s' "
				+ "AND studentIdA = '%s'",
				examID,
				netID);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		return appointments.size() > 0;
	}

	//Allows the user to cancel a student appointment, given the appointment id
	public synchronized void cancelAppointment(int appID) {
		logger.info("Cancelling appointment with ID " + appID);
		
		String queryString = String.format(
				"DELETE "
				+ "FROM appointment "
				+ "WHERE "
				+ "appointmentId=%d",
				appID
				);
		db.updateQuery(queryString);
	}
	
	//Return a list of all appointments, given a student's netID and the desired term
	public List<Appointment> showAppointments(String netID, int termId) {
		logger.info("Retrieving all appointments for student ID " + netID);
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "
				+ "LEFT JOIN exam "
				+ "ON appointment.examIdA = exam.examId "
				+ "LEFT JOIN course "
				+ "ON exam.courseId = course.courseTerm "
				+ "WHERE appointment.studentIdA='%s' "
				+ "AND course.termId = %d;",
				netID,
				termId
				);
		List<Map<String,Object>> appts = db.query(queryString);
		for (Map<String,Object> appt : appts) {
			String examId = (String) appt.get("examId");
			String netId = (String) appt.get("studentIdA");
			DateTime time = new DateTime((long) appt.get("dateIdA")*1000);
			
			Appointment newAppointment = new Appointment(examId, netId, time);
			appointments.add(newAppointment);
		}
		
		return appointments;
	}
	
	//Return a list of all appointments
	public List<Appointment> viewAllAppointments() {
		logger.info("Retrieving all appointments");
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "	);
		List<Map<String,Object>> appts = db.query(queryString);
		for (Map<String,Object> appt : appts) {
			String examId = (String) appt.get("examId");
			String netId = (String) appt.get("studentIdA");
			DateTime time = new DateTime((long) appt.get("dateIdA")*1000);
			
			Appointment newAppointment = new Appointment(examId, netId, time);
			appointments.add(newAppointment);
		}
		
		return appointments;
		
	}

	//Make a reservation for an exam, given the examID, start time, end time,
	//whether it is a course exam or an adhoc exam, and the instructor id
	public synchronized boolean makeReservation(Exam exam, DateTime start, DateTime end, boolean courseExam, String instructorId) {
		logger.severe("DOES NOT ADD ALL OF THE NECESSARY FIELDS. FIX THIS PLEASE.");
		
		logger.info("Creating new reservation request.");
		logger.fine("Exam ID: " + exam.getExamID());
		logger.fine("Exam start time: " + start.toString());
		logger.fine("Exam end time: " + end.toString());
		logger.fine("Course exam: " + courseExam);
		logger.fine("Reservation status: " + "P");
		logger.fine("Instructor ID: " + instructorId);
		
		String queryString = String.format("INSERT INTO exam "
				+ "(examId, start, end, boolCourseExam, examStatus, instructorIdA, numSeats, examLength, courseId) "
				+ "VALUES ('%s', %d, %d, '%s', '%s', '%s', %d, %d, '%s')", 
				exam.getExamID(), 
				start.getMillis()/1000,
				end.getMillis()/1000,
				courseExam ? 1 : 0,
				"P",
				instructorId,
				exam.getNumSeats(),
				exam.getLength(),
				exam.getCourseId()
				);
		db.updateQuery(queryString);
		
		// No longer needed if exams have a courseId attached.
		/*
		if (!exam.isAdHocExam()) {
			logger.info("Adding entry into courseexam database with course ID: " + exam.getCourseId());
			
			queryString = String.format("INSERT INTO courseexam "
					+ "(courseIdCE, examIdCE) "
					+ "VALUES ('%s', '%s')", 
					exam.getCourseId(),
					exam.getExamID()
					);
			db.updateQuery(queryString);
		}
		*/
		
		return true;
	}

	//cancel an exam given the particular combination of examId and instructorId
	public synchronized void cancelExam(String examId, String instructorId){
		logger.info("Cancelling exam with exam ID: " + examId);
		String queryString = String.format("DELETE FROM exam"
				+ " WHERE "
				+ "instructorIdA='%s'"
				+ " AND "
				+ "examId='%s'",
				instructorId,
				examId
				);
		db.updateQuery(queryString);
		
		// No longer needed, since exams have the courseId as well
		/*
		queryString = String.format("DELETE FROM courseexam"
				+ " WHERE "
				+ "examIdCE='%s'",
				instructorId,
				examId
				);
		db.updateQuery(queryString);
		*/
	}

	//retrieve a list of all exams
	public List<Exam> getAllExams() {
		logger.info("Retrieving all exams.");
		
		Database db = Database.getDatabase();
		List<Map<String,Object>> exams = db.query("SELECT examId, start, end, boolCourseExam, examStatus, instructorIdA, numSeats, courseId, examLength "
				+ "FROM exam");
		
		List<Exam> examsList = new ArrayList<Exam>();
		for (Map<String,Object> exam : exams) {
			
			String id = (String) exam.get("examId");
			DateTime start = new DateTime((long) exam.get("start")*1000);
			DateTime end = new DateTime((long) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			String instructorId = (String) exam.get("instructorId");
			int numSeats = (int) exam.get("numSeats");
			String courseId = (String) exam.get("courseId");
			int duration = (int) exam.get("examLength");
			boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
			
			Exam newExam = new Exam(id, start, end, examStatus, instructorId, courseId, numSeats, duration, adHocExam);
			
			examsList.add(newExam);
		}
		
		return examsList;
	}

	/*
	 * Returns a list of adHoc exams from the database.
	 * Not actually used by anything... so I commented it out
	 * 
	/*
	public List<Exam> getAdHocExams() {
		logger.info("Retrieving all ad hoc exams");
		Database db = Database.getDatabase();
		List<Map<String,Object>> adHocExams = db.query("SELECT (examId, start, end, examStatus, instructorId,examLength) FROM exam WHERE boolCourseExam = 0");
		
		List<OutsideExam> exams = new ArrayList<OutsideExam>();
		for (Map<String,Object> exam : adHocExams) {
			String id = (String) exam.get("examId");
			long startMilliseconds = new Long((int) exam.get("start")*1000);
			long endMilliseconds = new Long((int) exam.get("end")*1000);
			String status = (String) exam.get("examStatus");
			String instructorId = (String) exam.get("instructorId");
			int numSeats = (int) exam.get("numSeats");
			int duration = (int) exam.get("examLength");
			
			OutsideExam newExam = new OutsideExam(id, startMilliseconds, endMilliseconds, status, instructorId, numSeats,duration);
			exams.add(newExam);
		}
		
		return exams;
	}
	*/

	//retrieve a list of all exams, given a certain instructor id
	public List<Exam> getInstructorExams(String instructorId) {
		logger.info("Retrieving all exams for instructor with innstructor ID: " + instructorId);
		
		List<Exam> exams = new ArrayList<Exam>();
		String queryString = String.format("SELECT examId, start, end, boolCourseExam, examStatus, numSeats, examLength, courseId "
				+ "FROM exam "
				+ "WHERE exam.instructorIdA = '%s'",
				instructorId
				);
		List<Map<String,Object>> examList = db.query(queryString);
		for (Map<String,Object> exam : examList) {
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime((long) exam.get("start")*1000);
			DateTime end = new DateTime((long) exam.get("end")*1000);
			String status = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			int duration = (int) exam.get("examLength");
			String courseId = (String) exam.get("courseId");
			boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
			
			Exam newExam = new Exam(examId, start, end, status, instructorId, courseId, numSeats, duration, adHocExam);
			
			exams.add(newExam);
		}
		
		return exams;
	}

	//retrieve a list of all exams that are still pending
	public List<Exam> getPendingExams() {
		logger.info("Retrieving all pending exam reservation requests.");
		
		List<Map<String,Object>> exams = db.query(
				String.format("SELECT examId, start, end, boolCourseExam, examStatus, instructorIdA, numSeats, courseId, examLength "
				+ "FROM exam "
				+ "WHERE examStatus = 'P'"
				));
		
		List<Exam> examsList = new ArrayList<Exam>();
		for (Map<String,Object> exam : exams) {
			String id = (String) exam.get("examId");
			DateTime start= new DateTime((long) exam.get("start")*1000);
			DateTime end= new DateTime((long) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			String instructorId = (String) exam.get("instructorId");
			String courseId = (String) exam.get("courseId");
			int duration = (int) exam.get("examLength");
			boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
			
			Exam newExam = new Exam(id, start, end, examStatus, instructorId, courseId, numSeats, duration, adHocExam);
			
			examsList.add(newExam);
		}
		
		return examsList;
	}
	
	private void updateTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}
	
	private void updateUsersTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryUsersFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}
	
	private void updateClassTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryClassFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}
	
	private void updateInstructorTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryInstructorFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}
	
	private void updateStudentTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryStudentFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}
	
	private void updateCourseStudentTableFromFile(String filename, String tableName) throws FileNotFoundException, IOException {
		ArrayList<String> lines = new ArrayList<String>();
		String currentLine;
		
		FileReader reader = new FileReader(new File(filename));
		BufferedReader bReader = new BufferedReader(reader);
		while ((currentLine = bReader.readLine()) != null){
			lines.add(currentLine);
		}
		bReader.close();
		for(int i = 1; i < lines.size(); i++) {
			StringBuilder sb = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
			sb.append(queryCourseStudentFormat(lines.get(i)));
			sb.append(");");
			db.updateQuery(sb.toString());
			//System.out.println(lines.get(i));
		}
	}

	/*
	 * This method reads in the 3 .csv files that were provided to us and then stores that data in the 
	 * corresponding tables in our data base.
	 * (NOTE: At the moment the function will try to add an entry even if the Primary Key already exists.)
	 */
	public boolean updateData(String usersFileName, String instructorFileName, String coursesFileName, String rostersFileName) {
		logger.info("Reading csv files, updating database");
	
		try {
			//updateStudentTableFromFile(usersFileName, "student");
			
			//updateUsersTableFromFile(usersFileName, "users");
			
			//updateUsersTableFromFile(instructorFileName, "users");
			
			//updateInstructorTableFromFile(instructorFileName, "instructor");
			
			//updateClassTableFromFile(coursesFileName, "course");
			
			updateCourseStudentTableFromFile(rostersFileName, "coursestudent");
			
			
			return true;
			
		} catch (FileNotFoundException e) {
			logger.warning("File not found.");
			return false;
		} catch (IOException e) {
			logger.warning("An error occurred while reading file.");
			return false;
		}
		catch (Exception e) {
			logger.warning("Data was not formatted properly. Please check to make sure your input is correct, then try again.");
			return false;
		}
	}

	/*
	 * This internal function was written to take each line from the .csv file and put it into the format
	 * need for a query.
	 */
	private String queryFormat(String line) {
		String[] words = line.split(",");
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}
	
	private String queryStudentFormat(String line) {
		String[] wordsFromLine = line.split(",");
		StringBuilder sb = new StringBuilder("");
		
		String[] words = new String[5];
		words[0] = wordsFromLine[0];
		words[1] = wordsFromLine[1];
		words[2] = wordsFromLine[2];
		words[3] = wordsFromLine[3];
		words[4] = wordsFromLine[2];
		
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}
	
	private String queryCourseStudentFormat(String line) {
		String[] wordsFromLine = line.split(",");
		StringBuilder sb = new StringBuilder("");
		
		String[] words = new String[2];
		words[0] = wordsFromLine[1];
		words[1] = wordsFromLine[0];
		
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}
	
	private String queryUsersFormat(String line) {
		String[] wordsFromLine = line.split(",");
		StringBuilder sb = new StringBuilder("");
		
		String[] words = new String[4];
		words[0] = wordsFromLine[2];
		words[1] = wordsFromLine[0];
		words[2] = wordsFromLine[1];
		words[3] = wordsFromLine[3];
		
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}
	
	private String queryClassFormat(String line) {
		String[] wordsFromLine = line.split("[-,]");
		StringBuilder sb = new StringBuilder("");
		
		String[] words = new String[7];
		words[0] = wordsFromLine[0];
		words[1] = wordsFromLine[2];
		words[2] = wordsFromLine[3];
		words[3] = wordsFromLine[4];
		words[4] = wordsFromLine[5];
		words[5] = wordsFromLine[1];
		words[6] = wordsFromLine[0] + "-" + wordsFromLine[1];
		
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}
	
	private String queryInstructorFormat(String line) {
		String[] wordsFromLine = line.split(",");
		StringBuilder sb = new StringBuilder("");
		
		String[] words = new String[5];
		words[0] = wordsFromLine[2];
		words[1] = wordsFromLine[1];
		words[2] = wordsFromLine[0];
		words[3] = wordsFromLine[3];
		words[4] = wordsFromLine[2];
		
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			words[i] = words[i].replace("'", "''");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		//logger.info("sb.toString());
		return sb.toString();
		
	}

	//check in a student for a particular exam, given the student's netID
	public int checkIn(String netID) {
		DateTime now = DateTime.now();
		DateTime thirty = new DateTime(0,1,1,0,30);
		long nowM= now.getMillisOfDay()/60000;
		nowM = nowM %(60);
		long thirtyM = thirty.getMillisOfDay()/60000;
		DateTime search = null;
		if (nowM>thirtyM) {
			search = now.hourOfDay().roundHalfEvenCopy();
		} else {
			search = now.hourOfDay().roundFloorCopy();
			search = search.withMinuteOfHour(30);
		}
		List<Map<String,Object>> appointments = db.query(
				String.format("SELECT examIdA, studentIdA, dateIdA, seatIdA, appointmentID "
				+ "FROM appointment "
				+ "WHERE studentIDA = '%s' AND dateIdA = '%d'",
				netID,
				search.getMillis()/1000
				));
		int seat = -1;
		for (Map<String,Object> appointment : appointments) {	
		
			seat =  (int)appointment.get("seatIdA");
		}
		
		return seat;
	}

	public void produceStats() {
		
	}
	
		
	public void blockDay() {
		
	}
	
	

	/*
	 * The following are getters and setters for the testing center information.
	 */
	
	public void setNumberofSeats(int n) {
		logger.info("Changing number of seats.");
		logger.fine("Previous number of seats: " + numberOfSeats);
		
		numberOfSeats = n;
		
		logger.fine("New number of seats: " + numberOfSeats);
		
	}

	public int getNumSeats() {
		return numberOfSeats;
	}

	public void setSetAside(int n) {
		numberOfSetAside = n;
		
	}
	
	public int getSetAside() {
		return numberOfSetAside;
	}

	public void setGapTime(int h, int m) {
		logger.info("Changing gap time.");
		logger.fine("Previous gap time: " + gap.toString());
		
		gap = new Period(h,0,0,0);
		
		logger.fine("New gap time: " + gap.toString());
		
	}

	public int getGapTime() {
		return gap.getHours();
	}

	public void setReminder(int h) {
		logger.info("Changing reminder interval.");
		logger.fine("Previous reminder interval: " + reminderInt.toString());
		
		reminderInt = new Period(h,0,0,0);
		
		logger.fine("New reminder interval: " + reminderInt.toString());
		
	}

	public int getReminder() {
		return reminderInt.getHours();
		
	}

	//sets exam status of a certain exam of accepted, denied, or pending
	public void setExamStatus(String examId, String newStatus) {
		logger.info("Changing status of exam with exam ID: " + examId);
		logger.fine("New exam status: " + newStatus);
		
		Database db = Database.getDatabase();
		String queryString = String.format(
				"UPDATE exam "
				+ "SET examStatus='%s' "
				+ "WHERE examId='%s'",
				newStatus,
				examId
				);
		
		db.updateQuery(queryString);
	}
	
	/*
	 * 
		This function was not completed due to several errors that appeared in the last few hours.
	 */
	public synchronized boolean isExamSchedulable(Exam newExam) {
		this.makeReservation(newExam, newExam.getStart(), newExam.getEnd(), !newExam.isAdHocExam(), newExam.getInstructorId());
		DateTime now = DateTime.now();
		long nowUnix = now.getMillis()/1000;
		
		List<Map<String, Object>> exams = getOverlap(newExam);
		//TODO sort
		Map<Long, String[]> seatsAvailable = insertExisting(exams);
		
		for ( Map<String, Object> exam : exams ) {
			long start = (long) exam.get("start");
			long end = (long) exam.get("end");
			long len = (long)exam.get("examLength");
			String examId = (String) exam.get("examId");
			long gapPlusLen = len+(gap.getMinutes()*60);
			long rem = gapPlusLen%1800;
			long apLen = gapPlusLen;
			if(rem!=0) {
				long temp = apLen/1800 +1;
				apLen = temp*1800;
			}
			long apStart = end-apLen;
			long apEnd = end;

			List<Map<String, Object>> apps = db.query(String.format("SELECT appointmentId FROM appointment"
					+ "WHERE examIdA = '%s'",
					examId));
			int seatsLeft = (int) exam.get("numSeats") - apps.size();
			
			while(seatsLeft != 0) {
				if(apStart<start){
					this.cancelExam(newExam.getExamID(), newExam.getInstructorId());
					return false;
				}
				
				
				
				long searchTime = apEnd;
				ArrayList<String[]> appSeats = new ArrayList<String[]>();
				int i = 0;
				while(searchTime != apStart) {
					searchTime = searchTime -1800;
					if(!seatsAvailable.containsKey(searchTime)){
						seatsAvailable.put(searchTime, new String[numberOfSeats-numberOfSetAside]);
					}
					appSeats.add(i, seatsAvailable.get(searchTime));
				}
				
				for(int j = 0; j <numberOfSeats-numberOfSetAside;i++){
					searchTime = apEnd;
					boolean aval = true;
					for(String[] slot : appSeats) {
						if(slot[j] != null || slot[j-1] == examId||slot[j+1] == examId){
							aval = false;
							break;
						}
						if(aval) {
							for(String[] slotFill : appSeats) {
								slotFill[j] = examId;
							}
							seatsLeft--;
						}
					}
					
					
				
				}
				apEnd = apEnd - 1800;
				apStart = apStart-1800;
			}
		}
		
		this.cancelExam(newExam.getExamID(), newExam.getInstructorId());
		return true;
	}
	

	private Map<Long, String[]> insertExisting(List<Map<String, Object>> exams) {
		Map<Long, String[]> seatsAvailable = new HashMap<Long, String[]>();
		for(Map<String,Object> exam : exams) {
			String examId = (String) exam.get("StringIdA");

			List<Map<String, Object>> apps = db.query(String.format("SELECT startTime, endTime, examIdA, seatIdA "
					+ "FROM appointment"
					+ "WHERE examIdA = '%s'",
					examId));
			
			for(Map<String,Object> app : apps) {
				long searchTime = (long)app.get("endTime");
				while(searchTime != (long)app.get("startStart")) {
					searchTime = searchTime -1800;
					if(!seatsAvailable.containsKey(searchTime)){
						seatsAvailable.put(searchTime, new String[numberOfSeats-numberOfSetAside]);
					}
					String[] slot =  seatsAvailable.get(searchTime);
					slot[(int)app.get("seatIdA")] = (String) app.get("examIdA");
				}
			}
		}
		return seatsAvailable;
	}

	private List<Map<String, Object>> getOverlap(Exam newExam) {
		List<Map<String, Object>>fullList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> newExamEntry = db.query(String.format("SELECT examId, start,end,examStatus,numSeats,examLength,boolCourseExam,courseId,instructor.instructorId"
				+"FROM exam"
				+"WHERE examId = '%s'"
				, newExam.getExamID()));
		fullList.add(newExamEntry.get(0));
		fullList = getOverlap(newExamEntry.get(0), fullList);
		return fullList;
	}
	
	
	private List<Map<String, Object>> getOverlap(Map<String,Object> newExam,List<Map<String,Object>> fullList) {
		long start = (long) newExam.get("start");
		long end = (long) newExam.get("end");
			
		String queryString = String.format("SELECT examId, start, end, examStatus, numSeats, examLength, boolCourseExam,courseId,instructor.instructorId"
				+ "FROM exam"
				+ "WHERE (exam.start < '%s' AND exam.end > '%s')"
				+ "OR (exam.start < '%s' AND exam.end BETWEEN '%s' AND '%s')"
				+ "OR (exam.start > '%s' AND exam.start < '%s')",
				start, end, start, end, end, start, end
				);
		List<Map<String,Object>> examList = db.query(queryString);
		for (Map<String, Object> exam : examList) {
			
			if(!fullList.contains(exam)) {
				fullList.add(exam);
				fullList = getOverlap(exam,fullList);
			}
			
		};
		
		return fullList;
	}

	
//	private List<Map<String, Object>> getOverlap(Exam newExam) {
//		//Set set = new Set(); //prior to this method
//		//List numList = new List();
	
//		//set.add(newExam)
//		//SELECT examIds FROM Exam 
//		//WHERE start < this.start & end > this.end
//		//OR start < this.start & end > this.start & end < this.end
//		//OR start > this.start & start < this.end
//		//
//		//if(response==null){return;}
//		//else{ for each overlap, set.add(overlap) ; numList.add(numNewExams) if it is
//		//a new examId
//		// Iterator iter = set.iterator(); 
//		//while (iter.hasNext()){getOverlap(iter.next())}
//		//}
//		//after entire method is complete, print the static set & numList
//		//empty out both 	
//		return null;
//	}

	//retrieve a list of all exams that may be selected by a certain student
	public List<Exam> viewAvailableExams(Student st) {
		logger.info("Retrieving all exams currently available to student with ID" + st.getNetID());
		
		String queryString = String.format("SELECT exam.examId, start, end, examStatus, numSeats, boolCourseExam, instructorIdA, courseId, examLength "
				+ "FROM exam "
				+ "INNER JOIN coursestudent "
				+ "ON exam.courseId=coursestudent.courseIdCS "
				+ "WHERE coursestudent.studentIdCS='%s';", 
				st.getNetID());
		Database db = Database.getDatabase();
		List<Map<String, Object>> exams = db.query(queryString);
		
		List<Exam> availableExams = new ArrayList<Exam>();
		
		for (Map<String, Object> exam : exams) {
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime( (long) exam.get("start")*1000);
			DateTime end = new DateTime ( (long) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
			String instructorId = (String) exam.get("instructorId");
			int duration = (int) exam.get("examLength");
			String courseId = (String) exam.get("courseId");
			
			Exam newExam = new Exam(examId, start, end, examStatus, instructorId, courseId, numSeats, duration, adHocExam);
			
			availableExams.add(newExam);
		}
		
		return availableExams;
	}
	
	//notifier thread, specific to the testing center class
	private class Notifier extends Thread{
		private String threadName;
		private int count=0;
		private long sleepTime=0;
		
		Notifier(String name) {
		      super(name);
		      threadName=name;
		      System.out.println("Creating " +  threadName );
		      count++;
		      start();
		    }
		
		//calls getUpcoming at every half hour mark, and puts the thread to sleep
		//until then
		@Override
		public void run() {
			
			try {
		        while (true) {
		            System.out.println(new Date());
					  String msg = "Running"+threadName+" "+count;
				      logger.fine(msg);
		           // Thread.sleep(5 * 1000);
		            
					DateTime now = DateTime.now();
					DateTime thirty = new DateTime(0,1,1,0,30);
					DateTime sixty = new DateTime(0,1,1,1,0);
					long nowM= now.getMillisOfDay()/60000;
					nowM = nowM %(60);
					long thirtyM = thirty.getMillisOfDay()/60000;
					long sixtyM = sixty.getMillisOfDay()/60000;
					if (nowM<thirtyM) {
						sleepTime=thirtyM-nowM;
					} else {
						sleepTime=sixtyM-nowM;
					}
					System.out.println(sleepTime);
					logger.info("Current time:"+new Date()+"Sleep time:"+sleepTime);
					Thread.sleep(sleepTime * 1000 * 60);
					getUpcoming();
		        }
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		        logger.warning("An error occured while executing thread");
		    }
		}
		
		public String toString() {
		      return getName();
		    }
		/*
		 * Retrieves all appointments that will be coming up within the next half hour
		 */
		public void getUpcoming() {
			logger.info("Getting all upcoming appointments");
			DateTime now = DateTime.now();
			DateTime thirty = new DateTime(0,1,1,0,30);
			long nowM= now.getMillisOfDay()/60000;
			nowM = nowM %(60);
			long thirtyM = thirty.getMillisOfDay()/60000;
			DateTime search = null;
			if (nowM<thirtyM) {
				search = now.hourOfDay().roundFloorCopy();
				search = search.plusHours(reminderInt.getHours());
			} else {
				search = now.hourOfDay().roundFloorCopy();
				search = search.withMinuteOfHour(30);
				search = search.plusHours(reminderInt.getHours());
			}
			List<Map<String,Object>> appointments = db.query(
					String.format("SELECT examIdA, studentIdA, dateId, seatId, appointmentID "
					+ "FROM appointment "
					+ "WHERE dateId = '%d'",
					search.getMillis()/1000
					));
			
			
			for (Map<String,Object> appointment : appointments) {
				String queryString = String.format("SELECT examId, start, end, boolCourseExam, examStatus, instructorId, numSeats, examLength, courseId FROM exam"
						+ "WHERE examID = '%s'",
						appointment.get("examIdA"));
				List<Map<String,Object>> exams = db.query(queryString);
				
				queryString = String.format("SELECT studentId, email FROM student WHERE studentId = '%s'",
						appointment.get("studentIdA"));
				List<Map<String,Object>> emails = db.query(queryString);
				
				Map<String,Object>exam = exams.get(0);
				String examId = (String) exam.get("examId");
				DateTime start = new DateTime((long) exam.get("start")*1000);
				DateTime end = new DateTime((long) exam.get("end")*1000);
				String status = (String) exam.get("status");
				String instructorId = (String) exam.get("instructorId");
				String courseId = (String) exam.get("courseId");
				int numSeats = (int) exam.get("numSeats");
				int duration = (int) exam.get("examLength");
				boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
				
				Exam examObj = new Exam(examId, start, end, status, instructorId, courseId, numSeats, duration, adHocExam);				
				logger.info("Send email to: "+(String)emails.get(0).get("email"));
				sendNotice((String)emails.get(0).get("email"),examObj);
			}
		}

		/*
		 * This function creates and sends an email reminder to a student for an exam.
		 * (NOTE: At this time this is not automated.)
		 */
		public void sendNotice(String email, Exam exam) {
			final String username = "stonybrooktestingcenter@gmail.com";
			final String password = "testingcenter308";
			
			logger.info("Sending email");
			logger.fine("Recipient email address: " + email);
			logger.fine("Sender email address: " + username);
		
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
		
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
		
			try {
				String content = String.format("<h1>You have an exam coming up.</h1><br><br>"
		         		+ "The exam is scheduled to run from %s to %s.<br>"
		         		+ "Please arrive 30 minutes early to ensure that you can sign in "
		         		+ "and begin on-time.<br><br>"
		         		+ "Good luck!", 
		         		exam.getStart().toString(), 
		         		exam.getEnd().toString()
		         		);
		
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("from-email@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
				message.setSubject("UPCOMING EXAM");
				message.setContent(content, "text/html");
		
				Transport.send(message);
				
				logger.info("Email sent successfully.");
		
			} catch (MessagingException e) {
				logger.warning("Email could not be sent.");
				throw new RuntimeException(e);
			}
		}
		
	}
	
	/*
	 * 
	 */
	public double actualUtilization(LocalDate date) {
		long dateStartMillis = date.toDateTimeAtStartOfDay().getMillis();
		String queryString = String.format("SELECT startA, startB "
				+ "AS numAppointments "
				+ "FROM appointment "
				+ "WHERE startA BETWEEN %d AND %d",
				dateStartMillis + open.getMillisOfDay()/1000,
				dateStartMillis + close.getMillisOfDay()/1000
				);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		// Summation of the duration of every appointment scheduled for this day, in milliseconds.
		int totalDurationOccupied = 0;
		for (Map<String, Object> appointment : appointments) {
			int start = (int) appointment.get("startA") * 1000;
			int end = (int) appointment.get("endA") * 1000;
			
			totalDurationOccupied += (end - start) + gap.getMillis();
		}
		
		int totalDurationAvailable = numberOfSeats * (close.getMillisOfDay() - open.getMillisOfDay()); 
		
		return 1.0*totalDurationOccupied/totalDurationAvailable;
	}

	// appt.start < tc.close &&
	// appt.end > tc.open
	public double expectedUtilization(LocalDate date) {
		double expectedUtilization = actualUtilization(date);
		
		long dateStartMillis = date.toDateTimeAtStartOfDay().getMillis();
		String queryString = String.format("SELECT exam.examId, exam.start, exam.end, exam.numSeats, exam.examLength, COUNT(appointmentId) AS numAppointments "
				+ "FROM exam "
				+ "LEFT JOIN appointment "
				+ "ON exam.examId = appointment.examIdA "
				+ "WHERE exam.start < %d "
				+ "AND exam.end > %d "
				+ "GROUP BY exam.examId;",
				dateStartMillis + open.getMillisOfDay()/1000,
				dateStartMillis + close.getMillisOfDay()/1000
				);
		List<Map<String, Object>> exams = Database.getDatabase().query(queryString);
		for (Map<String, Object> exam : exams) {
			// Here I'm assuming that the duration of the exam will be in
			// seconds, just like the start and end times.
			int durationMillis = (int) exam.get("examLength") * 1000;
			int numSeats = (int) exam.get("numSeats");
			int numAppointments = (int) exam.get("numAppointments");
			long startMillis = (long) exam.get("start") * 1000;
			long endMillis = (long) exam.get("end") * 1000;
			
			LocalDate startDate = new LocalDate(startMillis);
			LocalDate endDate = new LocalDate(endMillis);
			long millisSpanned = endDate.toDateTimeAtStartOfDay().getMillis() - startDate.toDateTimeAtStartOfDay().getMillis();
			
			int timeOccupied = durationMillis + gap.getMillis();
			int studentsRemaining = numSeats - numAppointments;
			int daysSpanned = (int) millisSpanned/(1000*60*60*24);
			
			expectedUtilization += 1.0*timeOccupied*studentsRemaining/daysSpanned;
		}
		
		return expectedUtilization;
	}
	
	/**
	 * Takes the List<Map<String, Object>> returned by a DB query and outputs
	 * 	a List<Exam> by constructing an Exam object for each row.
	 * @param exams
	 * @return List<Exam> corresponding to the results of the query
	 * @precondition The query is called with the following fields:
	 * 		examId, start, end, examStatus, numSeats, boolCourseExam, instructorId, examLength, courseId
	 */
	private List<Exam> getExamListFromDBResult(List<Map<String,Object>> exams) {
		List<Exam> examList = new ArrayList<Exam>();
		
		for (Map<String, Object> exam : exams) {
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime( (long) exam.get("start")*1000);
			DateTime end = new DateTime ( (long) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			boolean adHocExam = ((String) exam.get("boolCourseExam")).equals("0");
			String instructorId = (String) exam.get("instructorIdA");
			int duration = (int) exam.get("examLength");
			String courseId = (String) exam.get("courseId");
			
			Exam newExam = new Exam(examId, start, end, examStatus, instructorId, courseId, numSeats, duration, adHocExam);
			
			examList.add(newExam);
		}
		
		return examList;
	}
	
	/**
	 * For each day in a specified term, report the number of student appointments on that day.
	 * Used for report a.
	 * 
	 * @param term	Integer code for the specified term
	 * @return
	 */
	public Map<LocalDate, Integer> appointmentsPerDay(int term) {
		Map<LocalDate, Integer> dailyCount = new HashMap<LocalDate, Integer>();
		
		String queryString = String.format("SELECT appointment.startTime "
				+ "FROM appointment "
				+ "INNER JOIN exam "
				+ "ON exam.examId=appointment.examIdA "
				+ "INNER JOIN course "
				+ "ON exam.courseId=course.courseTerm"
				+ " WHERE course.termId = %d;",
				term);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		for (Map<String, Object> appointment : appointments) {
			long startTime = (long) appointment.get("startTime");
			LocalDate date = new LocalDate(startTime);
			
			if (!dailyCount.containsKey(date)) {
				dailyCount.put(date, 1);
			}
			else {
				dailyCount.put(date, dailyCount.get(date) + 1);
			}
		}
		
		return dailyCount;
	}
	
	/**
	 * Returns the a map of a week (represented by the LocalDate corresponding
	 * to the Monday of that week) to the number of appointments that week.
	 * Used for report b
	 * @param term
	 * @return
	 */
	public synchronized Map<LocalDate, Integer> appointmentsPerWeek(int term) {
		Map<LocalDate, Integer> appts = new HashMap<LocalDate, Integer>();
		
		String queryString = String.format("SELECT appointment.startTime "
				+ "FROM appointment "
				+ "LEFT JOIN exam "
				+ "ON appointment.examIdA = exam.examId "
				+ "INNER JOIN course "
				+ "ON exam.courseId = course.courseTerm "
				+ "WHERE course.termId = %d "
				+ "ORDER BY appointment.startTime",
				term);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		LocalDate currMonday= new LocalDate(0);
		for (Map<String, Object> appointment : appointments) {
			System.out.println(appointment.get("startTime"));
			DateTime time = new DateTime( (long) appointment.get("startTime") * 1000 );
			
			LocalDate thisMonday = getMonday(time);
			if (!thisMonday.isEqual(currMonday)) {
				currMonday = thisMonday;
			}
			
			if (!appts.containsKey(currMonday)) {
				appts.put(currMonday, 1);
			} 
			else {
				appts.put(currMonday, appts.get(currMonday) + 1);
			}
		}
		
		return appts;
	}
	
	/**
	 * Returns the a map of a week (represented by the LocalDate corresponding
	 * to the Monday of that week) to the set of courseIds of the courses that
	 * use the TestingCenter that week.
	 * Used for report b
	 * @param term
	 * @return
	 */
	public synchronized Map<LocalDate, Set<String>> coursesPerWeek(int term) {
		Map<LocalDate, Set<String>> coursesPerWeek = new HashMap<LocalDate, Set<String>>();
		
		String queryString = String.format("SELECT appointment.startTime, course.courseTerm "
				+ "FROM appointment "
				+ "LEFT JOIN exam "
				+ "ON appointment.examIdA = exam.examId "
				+ "INNER JOIN course "
				+ "ON exam.courseId = course.courseTerm "
				+ "WHERE course.termId = %d "
				+ "ORDER BY appointment.startTime",
				term);
		List<Map<String, Object>> appointments = Database.getDatabase().query(queryString);
		
		LocalDate currMonday = null;
		for (Map<String, Object> appointment : appointments) {
			System.out.println(appointment.get("startTime"));
			DateTime time = new DateTime( (long) appointment.get("startTime") * 1000 );
			LocalDate thisMonday = getMonday(time);
			if (currMonday == null || !thisMonday.isEqual(currMonday)) {
				currMonday = thisMonday;
				coursesPerWeek.put(currMonday, new HashSet<String>());	
			}
			
			String termId = (String) appointment.get("courseTerm");
			coursesPerWeek.get(currMonday).add(termId);
		}
		
		return coursesPerWeek;
		
	}
	
	/**
	 * Determines the Monday of the week of the given DateTime.
	 * Used for report b.
	 * @param time
	 * @return
	 */
	private LocalDate getMonday(DateTime time) {
		int dayOfWeek = time.getDayOfWeek();
		DateTime monday = time.minusDays(dayOfWeek - 1);
		return monday.toLocalDate();
	}
	
	/**
	 * Returns the list of courses that use the Testing Center in the specified
	 * term.
	 * Used for report c
	 * @param term
	 * @return
	 */
	public List<Course> coursesUsed(int term) {
		List<Course> coursesResult = new ArrayList<Course>();
		String queryString = String.format("SELECT course.* "
				+ "FROM course "
				+ "INNER JOIN exam "
				+ "ON course.courseTerm = exam.courseId "
				+ "WHERE course.termId = %d;",
				term);
		List<Map<String, Object>> courses = Database.getDatabase().query(queryString);
		
		for (Map<String, Object> course : courses) {
			String courseId = (String) course.get("courseId");
			String subject = (String) course.get("subject");
			int catalogNumber = (int) course.get("catalogNumber");
			String section = (String) course.get("section");
			String instructorId = (String) course.get("instructorIdB");
			int termId = (int) course.get("termId");
			String courseTerm = (String) course.get("courseTerm");
			
			Course newCourse = new Course(courseId, subject, catalogNumber, section, instructorId, termId, courseTerm);
			
			coursesResult.add(newCourse);
		}
		
		return coursesResult;
	}
	
	/**
	 * Returns a map of the termId to the number of appointments that term.
	 * Used for report d
	 * @param startTerm
	 * @param endTerm
	 * @return
	 */
	public Map<Integer, Integer> appointmentsPerTerm(int startTerm, int endTerm) {
		Map<Integer, Integer> apptsPerTerm = new HashMap<Integer, Integer>();
		
		String queryString = String.format("SELECT course.termId, COUNT(1) AS numAppointments "
				+ "FROM appointment "
				+ "INNER JOIN exam "
				+ "ON appointment.examIdA = exam.examId "
				+ "INNER JOIN course "
				+ "ON exam.courseId = course.courseTerm "
				+ "WHERE course.termId >= %d "
				+ "AND course.termID <= %d "
				+ "GROUP BY termId;",
				startTerm,
				endTerm);
		List<Map<String, Object>> terms = Database.getDatabase().query(queryString);
		
		for (Map<String, Object> term : terms) {
			int termId = (int) term.get("termId");
			long numAppointments = (long) term.get("numAppointments");
			
			apptsPerTerm.put(termId, (int) numAppointments);
		}
		
		return apptsPerTerm;
	}
	
	public synchronized List<Student> viewAttendanceStats(String examId) {
		List<Student> studentsList = new ArrayList<Student>();
		
		String queryString = String.format("SELECT student.* "
				+ "FROM student "
				+ "INNER JOIN appointment "
				+ "ON student.studentId = appointment.studentIdA "
				+ "INNER JOIN exam "
				+ "ON appointment.examIdA = exam.examId "
				+ "WHERE examId = '%d';",
				examId);
		List<Map<String, Object>> students = Database.getDatabase().query(queryString);
		
		for (Map<String, Object> student : students) {
			String firstName = (String) student.get("firstName");
			String lastName = (String) student.get("lastName");
			String netID = (String) student.get("netID");
			String email = (String) student.get("email");
			String userIdB = (String) student.get("userIdB");
			
			Student newStudent = new Student(firstName, lastName, netID, email, userIdB);
			studentsList.add(newStudent);
		}
		
		return studentsList;
	}
	
/*
	public static void main(String[] args) {
		DateTime start = new DateTime(2015, 10, 29, 8, 0);
		DateTime end = new DateTime(2015, 10, 29, 14, 0);
		
		Exam ex = new Exam("test4", start, end, "sstoller", 64);
		
		TestingCenter tc = TestingCenter.getTestingCenter();
		
		System.out.println(tc.isExamSchedulable(ex));
		
		Student student = new Student(null, "a", null, null);
		List<Exam> exams = tc.viewAvailableExams(student);
		System.out.println("Lol");
		for (Exam exam : exams) {
			System.out.println(exam);
		}
	}
*/	
	
	public static void main(String[] args) {
		TestingCenter tc = getTestingCenter();
		
		Map<LocalDate, Integer> dailyCount = tc.appointmentsPerDay(1158);
		for (Entry<LocalDate, Integer> entry : dailyCount.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		
		List<Course> courses = tc.coursesUsed(1158);
		System.out.println(courses.size());
		for (Course course : courses) {
			System.out.println(course);
		}
		
		Map<Integer, Integer> apptsPerTerm = tc.appointmentsPerTerm(1150, 1160);
		System.out.println(apptsPerTerm);
		
		Map<LocalDate, Integer> apptsPerWeek = tc.appointmentsPerWeek(1158);
		System.out.println(apptsPerWeek);
		
		Map<LocalDate, Set<String>> coursesPerWeek = tc.coursesPerWeek(1158);
		System.out.println(coursesPerWeek);
		
		//tc.updateData("user.csv", "instructor.csv", "class.csv", "roster.csv");
	}

}
