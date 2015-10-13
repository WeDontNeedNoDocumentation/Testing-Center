import org.joda.time.DateTime;

/**
 * 
 */

/**
 * @author Daniel
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
