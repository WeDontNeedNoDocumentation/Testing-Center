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
	public CourseExam(String ExamID, DateTime start, DateTime end, String status, String instructorId) {
		super(ExamID, start, end, status);
		this.instructorId = instructorId;
	}
	
	public CourseExam(String ExamID, long start, long end, String status, String instructorId) {
		this(ExamID, new DateTime(start), new DateTime(end), status, instructorId);
	}
	
	

}
