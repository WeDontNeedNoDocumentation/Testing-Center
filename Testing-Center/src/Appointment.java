import java.util.Date;
import org.joda.time.DateTime;

public class Appointment {

	private int examID;
	private String StudentNetID;
	private DateTime time;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(int examID, String studentNetID, DateTime time) {
		this.examID = examID;
		StudentNetID = studentNetID;
		this.time = time;
	}
	
	public String getNetId() {
		return StudentNetID;
	}

}