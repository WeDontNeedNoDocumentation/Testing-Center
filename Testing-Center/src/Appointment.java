import java.util.Date;
import org.joda.time.DateTime;

/**
 * This class contains all of the information in an appointment for a student to take an exam.
 */

/**
 * @autor WdNnD
 * 
 */
public class Appointment {

	private String examID;
	private String StudentNetID;
	private DateTime time;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(String examID, String studentNetID, DateTime time) {
		this.examID = examID;
		StudentNetID = studentNetID;
		this.time = time;
	}
	
	public String getNetId() {
		return StudentNetID;
	}

}