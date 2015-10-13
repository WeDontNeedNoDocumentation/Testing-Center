import java.util.Date;
import org.joda.time.DateTime;

public class Appointment 
{
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

}