import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
	
	private static TestingCenter instance = null;
	
	private static final int DEFAULT_SEATS = 64;
	private static final int DEFAULT_SET_ASIDE = 0;
	private static final LocalTime DEFAULT_OPEN = new LocalTime(8,0);
	private static final LocalTime DEFAULT_CLOSE = new LocalTime(8,0);
	private static final Period DEFAULT_GAP = new Period(1,0,0,0);
	private static final Period DEFAULT_REMINDER_INTERVAL = new Period(1,0,0,0);
	
	private static final Logger LOGGER = Logger.getLogger(TestingCenter.class.getName());
	
	private List<Day> days;
	private int numberOfSeats;
	private int numberOfSetAside;
	private LocalTime open;
	private LocalTime close;
	private Period gap;
	private Period reminderInt;
	private Database db;
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
	}

	public static TestingCenter getTestingCenter() {
		if (instance == null) {
			instance = new TestingCenter();
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
	
	public void checkAppointment() {
		
	}
	
	public synchronized void cancelAppointment(String examId, String netID) {
		String queryString = String.format("DELETE FROM appointment "
				+ "WHERE "
				+ "studentIdA='%s'"
				+ " AND "
				+ "examId='%s'",
				netID,
				examId
				);
		db.updateQuery(queryString);
	}
	
	public List<Appointment> showAppointments(String netID) {
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "
				+ "WHERE studentIdA='%s'",
				netID
				);
		List<Map<String,Object>> appts = db.query(queryString);
		for (Map<String,Object> appt : appts) {
			System.out.println(appt);
			String examId = (String) appt.get("examId");
			String netId = (String) appt.get("studentIdA");
			DateTime time = new DateTime((int) appt.get("dateIdA")*1000);
			
			Appointment newAppointment = new Appointment(examId, netId, time);
			appointments.add(newAppointment);
		}
		
		return appointments;
	}
	
	public void produceStats() {
		
	}
	
		
	public void blockDay() {
		
	}
	
	/*
	 * This method reads in the 3 .csv files that were provided to us and then stores that data in the 
	 * corresponding tables in our data base.
	 * (NOTE: At the moment the function will try to add an entry even if the Primary Key already exists.)
	 */
	public boolean updateData() {
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
			System.out.println("File not found.");
			return false;
		} catch (IOException e) {
			System.out.println("An error occured while reading.");
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
		System.out.println(sb.toString());
		return sb.toString();
		
	}
		
	/*
	 * This function creates and sends an email reminder to a student for an exam.
	 * (NOTE: At this time this is not automated.)
	 */
	public void sendNotice(String email, Exam exam) {	
		final String username = "stonybrooktestingcenter@gmail.com";
		final String password = "testingcenter308";

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

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public synchronized void makeReservation(Exam exam, DateTime start, DateTime end, boolean courseExam, String status,String instructorId) {
		String queryString = String.format("INSERT INTO exam VALUES ("
				+ "'%s', %d, %d, %d, '%s', '%s')", 
				exam.getExamID(), 
				start.getMillis()/1000,
				end.getMillis()/1000,
				courseExam ? 0 : 1,
				status,
				instructorId
				);
		db.updateQuery(queryString);
	}
	
	public void getUpcoming() {
		
	}
	
	public synchronized void cancelExam(String examId, String instructorId){
		String queryString = String.format("DELETE FROM exam"
				+ " WHERE "
				+ "instructorId='%s'"
				+ " AND "
				+ "examId='%s'",
				instructorId,
				examId
				);
		db.updateQuery(queryString);
		
	}
	public List<Exam> getInstructorExams() {
		List<Exam> exams = new ArrayList<Exam>();
		String queryString = String.format("SELECT * FROM exam "
				+ "INNER JOIN instructor ON exam.instructorId=instructor.instructorId"
				);
		List<Map<String,Object>> examList = db.query(queryString);
		for (Map<String,Object> exam : examList) {
			System.out.println(exam);
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime((int) exam.get("start")*1000);
			DateTime end = new DateTime((int) exam.get("end")*1000);
			
			Exam newExam = new Exam(examId, start, end);
			exams.add(newExam);
		}
		
		return exams;
	}
	
	/*
	 * Returns a list of adHoc exams from the database.
	 */
	public List<OutsideExam> getAdHocExams() {
		Database db = Database.getDatabase();
		List<Map<String,Object>> adHocExams = db.query("SELECT (examId, localStart, localEnd) FROM exam WHERE boolCourseExam = 0");
		
		List<OutsideExam> exams = new ArrayList<OutsideExam>();
		for (Map<String,Object> exam : adHocExams) {
			String id = (String) exam.get("examId");
			long startMilliseconds = (long) exam.get("start")*1000;
			long endMilliseconds = (long) exam.get("end")*1000;
			
			OutsideExam newExam = new OutsideExam(id, new DateTime(startMilliseconds), new DateTime(endMilliseconds));
			exams.add(newExam);
		}
		
		return exams;
	}

	private class Notifier {
		
		
	}

	/*
	 * The following are getters and setters for the testing center information.
	 */
	
	public void setNumberofSeats(int n) {
		numberOfSeats = n;
		
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
		gap = new Period(h,0,0,0);
		
	}

	public int getGapTime() {
		return gap.getHours();
	}

	public void setReminder(int h) {
		reminderInt = new Period(h,0,0,0);
		
	}

	public int getReminder() {
		return reminderInt.getHours();
		
	}
	
}
