import java.util.Date;
import org.joda.time.DateTime;


public class Exam {

	protected String examID;
	protected DateTime start;
	protected DateTime end;
	
	
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


	

	public String getExamID() {
		return examID;
	}

}
