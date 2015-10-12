import java.util.Date;
import java.util.List;
import org.joda.time.LocalTime;
import org.joda.time.Period;

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
	private LocalTime open;
	private LocalTime close;
	private Period gap;
	private Period reminderInt;
	/**
	 * 
	 */
	
	public TestingCenter(List<Day> days, int numberOfSeats,
			int numberOfSetAside, LocalTime open, LocalTime close, Period gap,
			Period reminderInt) {
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
}
