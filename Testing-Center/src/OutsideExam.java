import org.joda.time.DateTime;

/**
 * Sub class used for any exam that is not associated with a class. 
 */

/**
 * @author WdNnD
 *
 */
public class OutsideExam extends Exam {
	
	public OutsideExam(String id, DateTime start, DateTime end, String status, String instructorId, int seats) {
		super(id, start, end, status, instructorId, seats);
	}

	public OutsideExam(String id, long start, long end, String status, String instructorId, int seats) {
		this(id, new DateTime(start), new DateTime(end), status, instructorId, seats);
	}

	private String organization;

}
