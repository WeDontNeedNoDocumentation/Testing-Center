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

	private String name;
	private String netID;
	private String email;
	private List<Appointment> appointments;
	private TestingCenter tC;
	/**
	 * 
	 */
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(String name, String netID, String email,
			List<Appointment> appointments) {
		this.name = name;
		this.netID = netID;
		this.email = email;
		this.appointments = appointments;
		tC = TestingCenter.getTestingCenter();
	}

	/*
	 * This is used to create an exam appointment for a specific exam.
	 * (NOTE: At this time no checks are made to see if this appointment is in any way valid.)
	 */
	public void makeAppointment(Exam exam, DateTime time, int seatId, int appointmentId) {
		tC.makeAppointment(exam, time, appointmentId, appointmentId, netID);
	}
	
	/*
	 * Will cancel this students appointment for the exam specified.
	 * (NOTE: This does not check to see if the appointment exists first. Will require the calling of
	 * view in the future.)
	 */
	public void cancelAppointment(String examId) {
		tC.cancelAppointment(examId, netID);
	}
	
	/*
	 * When called, returns a list of appointments associated with the student.
	 * (NOTE: Later this will be required to be called before canceling.)
	 */
	public List<Appointment> viewAppointments() {
		return tC.showAppointments(netID);
	}
	
	public void checkAvailability() {
		
	}
	
	public void viewExams() {
		
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