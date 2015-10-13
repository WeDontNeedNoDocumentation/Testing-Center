import java.util.Date;
import org.joda.time.DateTime;


public class Exam {

	private String examID;
	private DateTime start;
	private DateTime end;
	
	public Exam() {
		// TODO Auto-generated constructor stub
	}
	
	public Exam(String examID, DateTime start, DateTime end) {
		super();
		this.examID = examID;
		this.start = start;
		this.end = end;
	}

	public DateTime getStart() {
		return start;
	}
	
	public DateTime getEnd() {
		return end;
	}

}
