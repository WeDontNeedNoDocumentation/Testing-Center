package Java;
import java.util.Date;
import org.joda.time.DateTime;
import java.util.List;

/**
 * 
 */

/**
 * @author WdNnD
 *
 */
public class Day {

	private DateTime date;
	private boolean open;
	private List<TimeSlot> timeSlots;
	/**
	 * 
	 */
	public Day() {
		// TODO Auto-generated constructor stub
	}
	
	public Day(DateTime date, boolean open, List<TimeSlot> timeSlots) {
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
