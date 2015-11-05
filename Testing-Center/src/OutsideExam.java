import org.joda.time.DateTime;

/**
 * Sub class used for any exam that is not associated with a class. 
 */

/**
 * @author WdNnD
 *
 */
public class OutsideExam extends Exam {
	
	public OutsideExam(String id, DateTime start, DateTime end, String status, String instructorId, int seats, int duration) {
		super(id, start, end, status, instructorId, seats, duration);
	}

	public OutsideExam(String id, long start, long end, String status, String instructorId, int seats,int duration) {
		this(id, new DateTime(start), new DateTime(end), status, instructorId, seats, duration);
	}

	private String organization;

}
