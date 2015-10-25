import java.util.Date;
import org.joda.time.DateTime;

/**
 * Super class for all exams. Contians basic exam information.
 */

/**
 * 
 * @author WdNnD
 *
 */
public class Exam {

	protected String examID;
	protected DateTime start;
	protected DateTime end;
	protected String status;
	
	public Exam(String examID, DateTime start, DateTime end) {
		super();
		this.examID = examID;
		this.start = start;
		this.end = end;
		this.status = "P";
	}
	
	public Exam(String examID, DateTime start, DateTime end, String status) {
		super();
		this.examID = examID;
		this.start = start;
		this.end = end;
		this.status = status;
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
