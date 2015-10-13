import java.util.Date;
import org.joda.time.DateTime;


public class Exam {

	private int examID;
	private DateTime start;
	private DateTime end;
	
	public Exam() {
		// TODO Auto-generated constructor stub
	}
	
	public Exam(int examID, DateTime start, DateTime end) {
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

	public Object getId() {
		// TODO Auto-generated method stub
		return examID;
	}

}
