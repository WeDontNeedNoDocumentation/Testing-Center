import java.util.Date;
import java.util.List;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class Day {

	private Date date;
	private boolean open;
	private List<TimeSlot> timeSlots;
	/**
	 * 
	 */
	public Day() {
		// TODO Auto-generated constructor stub
	}
	
	public Day(Date date, boolean open, List<TimeSlot> timeSlots) {
		this.date = date;
		this.open = open;
		this.timeSlots = timeSlots;
	}
	
	public void displayTimeSlots() {
		
	}
	
	public void editTimeSlot() {
		
	}
	
	public void checkTimeSlots() {
		
	}

}
