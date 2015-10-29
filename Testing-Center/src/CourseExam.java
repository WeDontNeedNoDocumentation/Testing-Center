import org.joda.time.DateTime;

/**
 * This is a sub class specific to exams that have a course associated with them.
 */

/**
 * @author WdNdN
 *
 */
public class CourseExam extends Exam {
	
	private String instructorId;
	private Instructor instructor;
	/**
	 * 
	 */
	public CourseExam(String ExamID, DateTime start, DateTime end, String status, String instructorId, int numSeats) {
		super(ExamID, start, end, status, numSeats);
		this.instructorId = instructorId;
	}
	
	public CourseExam(String ExamID, long start, long end, String status, String instructorId, int numSeats) {
		this(ExamID, new DateTime(start), new DateTime(end), status, instructorId, numSeats);
	}
	
	

}
