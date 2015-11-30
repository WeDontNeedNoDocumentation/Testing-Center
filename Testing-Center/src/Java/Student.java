package Java;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * This class provides functionality for the student to interact with the TestingCenter.  An instance
 * of the class is created when the student logs in using information from the database.
 */

/**
 * @author Daniel
 *
 */
public class Student {

	private String firstName;
	private String lastName;
	private String netID;
	private String email;
	private String userIdB;
	
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	public Student(String firstName, String lastName, String netID, String email, String userIdB) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.netID = netID;
		this.email = email;
		this.userIdB = userIdB;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNetID() {
		return netID;
	}

	public void setNetID(String netID) {
		this.netID = netID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserIdB() {
		return userIdB;
	}

	public void setUserIdB(String userIdB) {
		this.userIdB = userIdB;
	}

	public TestingCenter gettC() {
		return tC;
	}

	/*
	 * This is used to create an exam appointment for a specific exam.
	 * (NOTE: At this time no checks are made to see if this appointment is in any way valid.)
	 */
	public void makeAppointment(Exam exam, DateTime time, int seatId, int appointmentId, DateTime start, DateTime end) {
		tC.makeAppointment(exam, time, seatId, appointmentId, netID, start, end);
	}
	
	/*
	 * Will cancel this students appointment for the exam specified.
	 * (NOTE: This does not check to see if the appointment exists first. Will require the calling of
	 * view in the future.)
	 */
	public void cancelAppointment(int string) {
		tC.cancelAppointment(string);
	}
	
	/*
	 * When called, returns a list of appointments associated with the student.
	 * (NOTE: Later this will be required to be called before canceling.)
	 */
	public List<Appointment> viewAppointments(int termId) {
		return tC.showAppointments(netID, termId);
	}
	
	public void checkAvailability() {
		
	}
	
	/*
	 * Displays reservations for all approved exams for courses this student is enrolled in.
	 */
	public List<Exam> viewExams() {
		return tC.viewAvailableExams(this);

	}
	
	/*
	public static void main(String args[]) {
		Student st = new Student();
		st.netID = "abak";
		
		Exam exam = new Exam("ex1", null, null);
		
		DateTime date = new DateTime(2000,1,1,1,1);
		
		
		st.makeAppointment(exam, date, 0, 0);
		
		List<Appointment> appts = st.viewAppointments();
		System.out.println(appts);
		
		st.cancelAppointment("ex1");
		
	}
	*/

}