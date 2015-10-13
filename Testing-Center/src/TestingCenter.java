import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
 * 
 */

/**
 * @author Daniel
 *
 */
public class TestingCenter {

	private List<Day> days;
	private int numberOfSeats;
	private int numberOfSetAside;
	private LocalTime open;
	private LocalTime close;
	private Period gap;
	private Period reminderInt;
	
	public TestingCenter() {
		
	}
	
	/**
	 * 
	 */
	public TestingCenter(List<Day> days, int numberOfSeats,
			int numberOfSetAside, LocalTime open, LocalTime close, Period gap,
			Period reminderInt) {
		this.days = days;
		this.numberOfSeats = numberOfSeats;
		this.numberOfSetAside = numberOfSetAside;
		this.open = open;
		this.close = close;
		this.gap = gap;
		this.reminderInt = reminderInt;
	}
	
	public void checkAvailability() {
		
	}
	
	public void makeAppointment() {
		
	}
	
	public void checkAppointment() {
		
	}
	
	public void produceStats() {
		
	}
	
	public void showAppointments() {
		
	}
	
	public void blockDay() {
		
	}
	
	public void updateData() {
		
	}
	
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
	
	public void makeReservation() {
		
	}
	
	public void getUpcoming() {
		
	}
	
	public void getInstructorExams() {
		
	}
	
	public void getAdHocExams() {
		
	}

	private class Notifier {
		
		
	}

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
	
	public static void main(String[] args) {
		TestingCenter tc = new TestingCenter();
		tc.sendNotice("hareldan95@gmail.com", new Exam("0", new DateTime(1,1,1,1,1), new DateTime(1,1,1,1,1)));
	}
}
