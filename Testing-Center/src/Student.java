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

	public void makeAppointment(Exam exam, DateTime time, int seatId, int appointmentId) {
		String queryString = String.format("INSERT INTO appointment VALUES ("
				+ "'%s', '%s', %d, %d, %d)", 
				exam.getExamID(), 
				this.netID, 
				time.getMillis()/1000,
				seatId,
				appointmentId
				);
		Database db = Database.getDatabase();
		db.updateQuery(queryString);
	}
	
	public void cancelAppointment(String examId) {
		String queryString = String.format("DELETE FROM appointment"
				+ "WHERE "
				+ "studentIdA='%s'"
				+ " AND "
				+ "examId='%s'",
				this.netID,
				examId
				);
	}
	
	public List<Appointment> viewAppointments() {
		List<Appointment> appointments = new ArrayList<Appointment>();
		String queryString = String.format("SELECT * FROM appointment "
				+ "WHERE studentIdA='%s'",
				this.netID
				);
		Database db = Database.getDatabase();
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
	
	public void checkAvailability() {
		
	}
	
	public void viewExams() {
		
	}
	
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

}