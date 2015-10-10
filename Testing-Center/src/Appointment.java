import java.util.Date;


public class Appointment {

	private String examID;
	private String StudentNetID;
	private Date time;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Appointment(String examID, String studentNetID, Date time) {
		this.examID = examID;
		StudentNetID = studentNetID;
		this.time = time;
	}

}
