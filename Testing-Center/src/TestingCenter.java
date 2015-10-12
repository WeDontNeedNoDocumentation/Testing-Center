import java.util.Date;
import java.util.List;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class TestingCenter {

	private List<Day> days;
	private int numberOfSeats;
	private int numberOfSetAside;
	private int open;
	private int close;
	private int gap;
	private int reminderInt;
	/**
	 * 
	 */
	
	public TestingCenter(List<Day> days, int numberOfSeats,
			int numberOfSetAside, int open, int close, int gap,
			int reminderInt) {
		this.days = days;
		this.numberOfSeats = numberOfSeats;
		this.numberOfSetAside = numberOfSetAside;
		this.open = open;
		this.close = close;
		this.gap = gap;
		this.reminderInt = reminderInt;
	}
	
	public void checkAvailability() {
		
	}
	
	public void makeAppointment() {
		
	}
	
	public void checkAppointment() {
		
	}
	
	public void produceStats() {
		
	}
	
	public void showAppointments() {
		
	}
	
	public void blockDay() {
		
	}
	
	public void updateData() {
		
	}
	
	public void sendNotice() {
		
	}
	
	public void makeReservation() {
		
	}
	
	public void getUpcoming() {
		
	}
	
	public void getInstructorExams() {
		
	}
	
	public void getAdHocExams() {
		
	}

	private class Notifier {
		
		
	}

	public void setNumberofSeats(int n) {
		// TODO Auto-generated method stub
		
	}

	public int getNumSeats() {
		return numberOfSeats;
	}
}
