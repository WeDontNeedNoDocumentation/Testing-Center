import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

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
		}
		return instance;
	}
	/*
	 * (NOTE: Many of these functions have been implemented but their functionality are in a different class 
	 * for the moment. For the next submission the project will be organized correctly or the design will be
	 * changed. For Example- makeAppointment is currently being done in Student but at this time it does not
	 * use checkAvailability().) 
	 */
	
	public synchronized void checkAvailability() {
		
	}
	
	public synchronized void makeAppointment(Exam exam, DateTime time, int seatId, int appointmentId, String netID) {
		logger.info("Creating new Appointment");
		logger.fine("Exam id: " + exam.getExamID());
		logger.fine("Student ID: " + netID);
		logger.fine("Appointment start time: " + time.toString());
		logger.fine("Seat ID: " + seatId);
		logger.fine("Appointment ID: " + appointmentId);
		
		String queryString = String.format("INSERT INTO appointment VALUES ("
				+ "'%s', '%s', %d, %d, %d)", 
				exam.getExamID(), 
				netID, 
				time.getMillis()/1000,
				seatId,
				appointmentId
				);
		db.updateQuery(queryString);
	}
	
	
	
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
	
	public List<Appointment> showAppointments(String netID) {
		logger.info("Retrieving all appointments for student ID " + netID);
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "
				+ "WHERE studentIdA='%s'",
				netID
				);
		List<Map<String,Object>> appts = db.query(queryString);
		for (Map<String,Object> appt : appts) {
			String examId = (String) appt.get("examId");
			String netId = (String) appt.get("studentIdA");
			DateTime time = new DateTime((int) appt.get("dateIdA")*1000);
			
			Appointment newAppointment = new Appointment(examId, netId, time);
			appointments.add(newAppointment);
		}
		
		return appointments;
	}
	
	public List<Appointment> viewAllAppointments() {
		logger.info("Retrieving all appointments");
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "	);
		List<Map<String,Object>> appts = db.query(queryString);
		for (Map<String,Object> appt : appts) {
			String examId = (String) appt.get("examId");
			String netId = (String) appt.get("studentIdA");
			DateTime time = new DateTime((int) appt.get("dateIdA")*1000);
			
			Appointment newAppointment = new Appointment(examId, netId, time);
			appointments.add(newAppointment);
		}
		
		return appointments;
		
	}

	public synchronized boolean makeReservation(Exam exam, DateTime start, DateTime end, boolean courseExam, String instructorId) {
		logger.info("Creating new reservation request.");
		logger.fine("Exam ID: " + exam.getExamID());
		logger.fine("Exam start time: " + start.toString());
		logger.fine("Exam end time: " + end.toString());
		logger.fine("Course exam: " + courseExam);
		logger.fine("Reservation status: " + "P");
		logger.fine("Instructor ID: " + instructorId);
		
		String queryString = String.format("INSERT INTO exam "
				+ "(examId, start, end, boolCourseExam, examStatus, instructorId, numSeats) "
				+ "VALUES ('%s', %d, %d, %d, '%s', '%s', %d)", 
				exam.getExamID(), 
				start.getMillis()/1000,
				end.getMillis()/1000,
				courseExam ? 0 : 1,
				"P",
				instructorId,
				exam.getNumSeats()
				);
		db.updateQuery(queryString);
		
		if (exam instanceof CourseExam) {
			CourseExam ce = (CourseExam) exam;
			logger.info("Adding entry into courseexam database with course ID: " + ce.getCourseId());
			
			queryString = String.format("INSERT INTO courseexam "
					+ "(courseIdCE, examIdCE) "
					+ "VALUES ('%s', '%s')", 
					ce.getCourseId(),
					exam.getExamID()
					);
			db.updateQuery(queryString);
		}
		
		return true;
	}

	public synchronized void cancelExam(String examId, String instructorId){
		logger.info("Cancelling exam with exam ID: " + examId);
		String queryString = String.format("DELETE FROM exam"
				+ " WHERE "
				+ "instructorId='%s'"
				+ " AND "
				+ "examId='%s'",
				instructorId,
				examId
				);
		db.updateQuery(queryString);
		
		queryString = String.format("DELETE FROM courseexam"
				+ " WHERE "
				+ "examIdCE='%s'",
				instructorId,
				examId
				);
		db.updateQuery(queryString);
		
	}

	public List<Exam> getAllExams() {
		logger.info("Retrieving all exams.");
		
		Database db = Database.getDatabase();
		List<Map<String,Object>> exams = db.query("SELECT examId, start, end, boolCourseExam, examStatus, instructorId, courseexam.courseIdCE "
				+ "FROM exam "
				+ "LEFT JOIN courseexam "
				+ "ON exam.examId=courseexam.examIdCE");
		
		List<Exam> examsList = new ArrayList<Exam>();
		for (Map<String,Object> exam : exams) {
			
			String id = (String) exam.get("examId");
			long startMilliseconds = new Long((int) exam.get("start")*1000);
			long endMilliseconds = new Long((int) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			String instructorId = (String) exam.get("instructorId");
			int numSeats = (int) exam.get("numSeats");
			String courseId = (String) exam.get("courseIdCE");
			
			Exam newExam = ((String) exam.get("boolCourseExam")).equals("1") ? 
					new CourseExam(id, startMilliseconds, endMilliseconds, examStatus, instructorId, courseId, numSeats) : 
						new OutsideExam(id, startMilliseconds, endMilliseconds, examStatus, instructorId, numSeats); 
			
			examsList.add(newExam);
		}
		
		return examsList;
	}

	/*
	 * Returns a list of adHoc exams from the database.
	 * Not actually used by anything...
	 */
	public List<OutsideExam> getAdHocExams() {
		logger.info("Retrieving all ad hoc exams");
		Database db = Database.getDatabase();
		List<Map<String,Object>> adHocExams = db.query("SELECT (examId, start, end, examStatus, instructorId) FROM exam WHERE boolCourseExam = 0");
		
		List<OutsideExam> exams = new ArrayList<OutsideExam>();
		for (Map<String,Object> exam : adHocExams) {
			String id = (String) exam.get("examId");
			long startMilliseconds = new Long((int) exam.get("start")*1000);
			long endMilliseconds = new Long((int) exam.get("end")*1000);
			String status = (String) exam.get("examStatus");
			String instructorId = (String) exam.get("instructorId");
			int numSeats = (int) exam.get("numSeats");;
			
			OutsideExam newExam = new OutsideExam(id, startMilliseconds, endMilliseconds, status, instructorId, numSeats);
			exams.add(newExam);
		}
		
		return exams;
	}

	public List<Exam> getInstructorExams(String instructorId) {
		logger.info("Retrieving all exams for instructor with innstructor ID: " + instructorId);
		
		List<Exam> exams = new ArrayList<Exam>();
		String queryString = String.format("SELECT * FROM exam "
				+ "INNER JOIN instructor ON exam.instructorId=instructor.instructorId WHERE exam.instructorId = '%s'",
				instructorId
				);
		List<Map<String,Object>> examList = db.query(queryString);
		for (Map<String,Object> exam : examList) {
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime(new Long((int) exam.get("start")*1000));
			DateTime end = new DateTime(new Long((int) exam.get("end")*1000));
			String status = (String) exam.get("status");
			int numSeats = (int) exam.get("numSeats");
			
			Exam newExam = new Exam(examId, start, end, status, numSeats);
			exams.add(newExam);
		}
		
		return exams;
	}

	public List<Exam> getPendingExams() {
		logger.info("Retrieving all pending exam reservation requests.");
		
		List<Map<String,Object>> exams = db.query(
				String.format("SELECT examId, start, end, boolCourseExam, examStatus, instructorId, numSeats, courseexam.courseIdCE "
				+ "FROM exam "
				+ "LEFT JOIN courseexam "
				+ "ON exam.examId=courseexam.examIdCE "
				+ "WHERE examStatus = 'P'"
				));
		
		List<Exam> examsList = new ArrayList<Exam>();
		for (Map<String,Object> exam : exams) {
			
			Exam newExam = null;
			
			String id = (String) exam.get("examId");
			long startMilliseconds = new Long((int) exam.get("start")*1000);
			long endMilliseconds = new Long((int) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			String instructorId = (String) exam.get("instructorId");
			
			if ( ((String) exam.get("boolCourseExam")).equals("1") ) {
				String courseId = (String) exam.get("courseIdCE");
				newExam = new CourseExam(id, startMilliseconds, endMilliseconds, examStatus, instructorId, courseId, numSeats);
			}
			else {
				newExam = new OutsideExam(id, startMilliseconds, endMilliseconds, examStatus, instructorId, numSeats);
			}
			
			examsList.add(newExam);
		}
		
		return examsList;
	}

	/*
	 * This method reads in the 3 .csv files that were provided to us and then stores that data in the 
	 * corresponding tables in our data base.
	 * (NOTE: At the moment the function will try to add an entry even if the Primary Key already exists.)
	 */
	public boolean updateData() {
		logger.info("Reading csv files, updating database");
		ArrayList<String> lines = new ArrayList<String>();
		
		
		try {
			String currentLine;
			
			FileReader reader = new FileReader(new File("user.csv"));
			BufferedReader bReader = new BufferedReader(reader);
			while ((currentLine = bReader.readLine()) != null){
				lines.add(currentLine);
			}
			bReader.close();
			for(int i = 1; i < lines.size(); i++) {
				StringBuilder sb = new StringBuilder("INSERT INTO student VALUES (");
				sb.append(queryFormat(lines.get(i)));
				sb.append(");");
				db.query(sb.toString());
				//System.out.println(lines.get(i));
			}
			
			lines = new ArrayList<String>();
			FileReader reader2 = new FileReader(new File("class.csv"));
			BufferedReader bReader2 = new BufferedReader(reader2);
			while ((currentLine = bReader2.readLine()) != null){
				lines.add(currentLine);
			}
			bReader2.close();
			for(int i = 1; i < lines.size(); i++) {
				StringBuilder sb = new StringBuilder("INSERT INTO course VALUES (");
				sb.append(queryFormat(lines.get(i)));
				sb.append(");");
				db.query(sb.toString());
				//System.out.println(lines.get(i));
			}
			
			lines = new ArrayList<String>();
			FileReader reader3 = new FileReader(new File("roster.csv"));
			BufferedReader bReader3 = new BufferedReader(reader3);
			while ((currentLine = bReader3.readLine()) != null){
				lines.add(currentLine);
			}
			bReader3.close();
			for(int i = 1; i < lines.size(); i++) {
				StringBuilder sb = new StringBuilder("INSERT INTO coursestudent VALUES (");
				sb.append(queryFormat(lines.get(i)));
				sb.append(");");
				db.query(sb.toString());
				//System.out.println(lines.get(i));
			}
			
			return true;
			
		} catch (FileNotFoundException e) {
			logger.warning("File not found.");
			return false;
		} catch (IOException e) {
			logger.warning("An error occured while reading.");
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
Okay so I think I figured out the algo
I'm assuming that scheduling periods have to be contiguous. The algorithm I have depends on that. So for example, you can schedule 100 seats for today and tomorrow, but you can't say that you need 100 seats between today and the day after tomorrow.
So under that assumption:
Sort the current requests (plus the request that you're testing for) in reverse order of END time.
Fill in seats assuming that every seat is filled as late as possible
So if you have an exam that needs 100 seats, and you have 75 seats for today and 75 seats for tomorrow, then assume all 75 seats will be filled tomorrow, and 25 will be filled today.
Do this for every exam in reverse order of end time.
If you can fill all seats before you hit the current time, then the course is schedulable.
	 */
	public boolean isExamSchedulable(Exam newExam) {
		this.makeReservation(newExam, newExam.getStart(), newExam.getEnd(), newExam instanceof CourseExam, newExam.getInstructorId());
		Database db = Database.getDatabase();
		DateTime now = DateTime.now();
		long nowUnix = now.getMillis()/1000;
		List<Map<String, Object>> exams = db.query(String.format(
				"SELECT exam.examId, COUNT(appointment.examIdA) AS numAppointments, exam.start, exam.end, exam.numSeats "
				+ "FROM exam "
				+ "LEFT JOIN appointment "
				+ "ON exam.examId=appointment.examIdA "
				+ "WHERE end > %d "
				+ "AND exam.examStatus <> 'R' "
				+ "GROUP BY exam.examId "
				+ "ORDER BY end DESC, start DESC;",
				nowUnix
				));
		
		Map<LocalDate, Integer> seatsAvailable = new HashMap<LocalDate, Integer>();

		for ( Map<String, Object> exam : exams ) {
			
			long seatsNeeded = (int) exam.get("numSeats") - (long) exam.get("numAppointments");
			
			long endTime = new Long((int) exam.get("end")*1000);
			LocalDate endDate = new LocalDate(endTime);
			
			long startTime = new Long((int) exam.get("end")*1000);
			LocalDate startDate = new LocalDate(startTime);
			
			LocalDate currDate = endDate;
			
			if (!seatsAvailable.containsKey(currDate))
				seatsAvailable.put(currDate, numberOfSeats);
			while (seatsNeeded > 0) {
				if (currDate.compareTo(startDate) < 0) {
					this.cancelExam(newExam.getExamID(), newExam.getInstructorId());
					return false;
				}
				int seatsAvailableToday = seatsAvailable.get(currDate);
				if (seatsAvailableToday < seatsNeeded) {
					seatsAvailableToday = 0;
					seatsNeeded -= seatsAvailableToday;
				}
				else {
					seatsAvailableToday -= seatsNeeded;
					seatsNeeded = 0;
				}
				seatsAvailable.put(currDate, seatsAvailableToday);
				
				if (seatsNeeded > 0)
					currDate = currDate.minusDays(1);
			}
		}
		
		this.cancelExam(newExam.getExamID(), newExam.getInstructorId());
		return true;
	}
	
	public List<Exam> viewAvailableExams(Student st) {
		logger.info("Retrieving all exams currently available to student with ID" + st.getNetID());
		
		String queryString = String.format("SELECT exam.examId, start, end, examStatus, numSeats, boolCourseExam, instructorId, courseexam.courseIdCE "
				+ "FROM exam "
				+ "INNER JOIN courseexam "
				+ "ON exam.examId=courseexam.examIdCE "
				+ "INNER JOIN coursestudent "
				+ "ON courseexam.courseIdCE=coursestudent.courseIdCS "
				+ "WHERE coursestudent.studentIdCS='%s';", 
				st.getNetID());
		Database db = Database.getDatabase();
		List<Map<String, Object>> exams = db.query(queryString);
		
		List<Exam> availableExams = new ArrayList<Exam>();
		
		for (Map<String, Object> exam : exams) {
			Exam newExam;
			
			String examId = (String) exam.get("examId");
			long startMilliseconds = new Long((int) exam.get("start")*1000);
			long endMilliseconds = new Long((int) exam.get("end")*1000);
			String examStatus = (String) exam.get("examStatus");
			int numSeats = (int) exam.get("numSeats");
			boolean courseExam = ((String) exam.get("boolCourseExam")).equals("1") ? true : false;
			String instructorId = (String) exam.get("instructorId");
			
			if (courseExam) {
				String courseId = (String) exam.get("courseIdCE");
				newExam = new CourseExam(examId, startMilliseconds, endMilliseconds, examStatus, instructorId, courseId, numSeats);
			}
			else {
				newExam = new OutsideExam(examId, endMilliseconds, endMilliseconds, examStatus, instructorId, numSeats);
			}
			
			availableExams.add(newExam);
		}
		
		return availableExams;
	}
	
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
		
		@Override
		public void run() {
			
			try {
		        while (true) {
		            System.out.println(new Date());
					  String msg = "Running"+threadName+" "+count;
					  //getUpcoming();
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
		 * This still needs to be tested
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
					String.format("SELECT examIdA, studentIdA, dateIdA, seatIdA, appointmentID "
					+ "FROM appointment "
					+ "WHERE dateIdA = '%d'",
					search.getMillis()/1000
					));
			
			
			for (Map<String,Object> appointment : appointments) {
				String queryString = String.format("SELECT examId, start, end, boolCourseExam, examStatus, instructorId FROM exam"
						+ "WHERE examID = '%s'",
						appointment.get("examIdA"));
				List<Map<String,Object>> exams = db.query(queryString);
				
				queryString = String.format("SELECT studentId, email FROM student WHERE studentId = '%s'",
						appointment.get("studentIdA"));
				List<Map<String,Object>> emails = db.query(queryString);
				
				Map<String,Object>exam = exams.get(0);
				String examId = (String) exam.get("examId");
				DateTime start = new DateTime(new Long((int) exam.get("start")*1000));
				DateTime end = new DateTime(new Long((int) exam.get("end")*1000));
				String status = (String) exam.get("status");
				int numSeats = (int) exam.get("numSeats");
				
				Exam examObj = new Exam(examId, start, end, status, numSeats);
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
	
}
