import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
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

/**
 * 
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
	}

	public void makeAppointment() {
		
	}
	
	public void cancelAppointment() {
		
	}
	
	public void viewAppointments() {
		
	}
	
	public void checkAvailability() {
		
	}
	
	public void viewExams() {
		
	}

}