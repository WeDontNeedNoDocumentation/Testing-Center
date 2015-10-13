import org.joda.time.DateTime;

/**
 * This is a sub class specific to exams that have a course associated with them.
 */

/**
 * @author WdNdN
 *
 */
public class CourseExam extends Exam {
	
	private Instructor instrucor;
	/**
	 * 
	 */
	public CourseExam(String ExamID, DateTime start, DateTime end) {
		super(ExamID, start, end);
	}

}
