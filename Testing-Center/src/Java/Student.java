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
	 */
	public boolean makeAppointment(Exam exam, DateTime time, int appointmentId, DateTime start, DateTime end) {
		return tC.makeAppointment(exam, time, appointmentId, netID, start, end);
	}
	
	/*
	 * Will cancel this students appointment for the exam specified.
	 */
	public boolean cancelAppointment(int string) {
		return tC.cancelAppointment(string);
	}
	
	/*
	 * When called, returns a list of appointments associated with the student.
	 * (NOTE: This is required to be called before canceling.)
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

}