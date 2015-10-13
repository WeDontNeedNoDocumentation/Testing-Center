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
	
	/*
	 * New. Add this to the documentation.
	 */
	
	public void sendReminder(Exam exam) {
		
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
		/*
		// Recipient's email ID needs to be mentioned.
	      String recipientAddress = email;

	      // Sender's email ID needs to be mentioned
	      String from = "DO_NOT_REPLY@testingcenter.stonybrook.com";

	      // Assuming you are sending email from gmail
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.put("mail.smtp.starttls.enable", "true"); 
	      properties.put("mail.smtp.host", host);
	      properties.put("mail.smtp.user", "stonybrooktestingcenter@gmail.com"); // User name
	      properties.put("mail.smtp.password", "testingcenter"); // password
	      properties.put("mail.smtp.port", "465");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      properties.put("mail.smtp.socketFactory.fallback","false");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress));

	         // Set Subject: header field
	         message.setSubject("UPCOMING EXAM");

	         // Send the actual HTML message, as big as you like
	         String content = String.format("<h1>You have an exam coming up.</h1><br><br>"
		         		+ "The exam is scheduled to run from %s to %s.<br>"
		         		+ "Please arrive 30 minutes early to ensure that you can sign in "
		         		+ "and begin on-time.<br><br>"
		         		+ "Good luck!", 
		         		exam.getStart().toString(), 
		         		exam.getEnd().toString()
		         		);
	         message.setContent(content, "text/html" );

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      }
	}
	*/
	
	public static void main(String[] args) {
		Student student = new Student("Dan", "109074254", "hareldan95@gmail.com", new ArrayList<Appointment>());
		student.sendReminder(new Exam("0", new DateTime(1,1,1,1,1), new DateTime(1,1,1,1,1)));
	}

}