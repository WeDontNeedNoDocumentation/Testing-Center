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
	protected String instructorId;
	protected int numSeats;
	
	public Exam(String examID, DateTime start, DateTime end, String instructorId, int seats) {
		this(examID, start, end, "P", instructorId, seats);
	}
	
	public Exam(String examID, DateTime start, DateTime end, String status, String instructorId, int seats) {
		super();
		this.examID = examID;
		this.start = start;
		this.end = end;
		this.status = status;
		this.instructorId = instructorId;
		this.numSeats = seats;
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
	
	public String getInstructorId() {
		return instructorId;
	}
	
	public boolean isSchedulable() {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.isExamSchedulable(this);
	}

	public Object getNumSeats() {
		// TODO Auto-generated method stub
		return numSeats;
	}
	
	public String toString() {
		String s = String.format("{"
				+ "examId:%s, "
				+ "startTime:%s, "
				+ "endTime:%s, "
				+ "status:%s, "
				+ "instructorId:%s, "
				+ "numSeats:%d",
				examID,
				start.toString(),
				end.toString(),
				status,
				instructorId,
				numSeats);
		if (this instanceof CourseExam) {
			s += ", courseId:" + ((CourseExam) this).getCourseId();
		}
		s += "}";
		
		return s;
		
	}

}
