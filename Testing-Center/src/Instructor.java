import org.joda.time.DateTime;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class Instructor {

	private String name;
	private String email;
	private TestingCenter tC;
	
	/**
	 * 
	 */
	
	public Instructor(String name, String email, TestingCenter tC) {
		this.name = name;
		this.email = email;
		this.tC = tC;
	}
	
	public void viewExams() {
		
	}
	
	public void makeExam(String ID, DateTime start, DateTime end) {
		Exam exam = new CourseExam(ID,start,end);
		tC.makeReservation(exam);
	}
	
	public void viewAvailability() {
		
	}
	
	public void cancelExams() {
		
	}
	
	public void viewAttendanceStats() {
		
	}

}
