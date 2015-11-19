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
	private String courseId;
	/**
	 * 
	 */
	public CourseExam(String ExamID, DateTime start, DateTime end, String status, String instructorId, String courseId, int numSeats, int duration) {
		super(ExamID, start, end, status, instructorId, numSeats, duration);
		this.instructorId = instructorId;
		this.courseId = courseId;
	}
	
	public CourseExam(String ExamID, long start, long end, String status, String instructorId, String courseId, int numSeats, int duration) {
		this(ExamID, new DateTime(start), new DateTime(end), status, instructorId, courseId, numSeats, duration);
	}

	public Object getCourseId() {
		// TODO Auto-generated method stub
		return courseId;
	}
	
	

}