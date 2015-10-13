import java.util.Date;
import org.joda.time.DateTime;


public class Appointment 
{
=======
/**
 * This class contains all of the information in an appointment for a student to take an exam.
 */

/**
 * @autor WdNnD
 * 
 */
public class Appointment {

>>>>>>> branch 'master' of https://github.com/WeDontNeedNoDocumentation/Testing-Center.git
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