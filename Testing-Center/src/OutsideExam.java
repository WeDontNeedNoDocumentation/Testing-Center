import org.joda.time.DateTime;

/**
 * Sub class used for any exam that is not associated with a class. 
 */

/**
 * @author WdNnD
 *
 */
public class OutsideExam extends Exam {
	
	public OutsideExam(String id, DateTime dateTime, DateTime dateTime2) {
		super(id, dateTime, dateTime2);
	}

	private String organization;

}
