import java.util.List;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class Student {

	private String name;
	private String netID;
	private String email;
	private List<Appointment> appointments;
	/**
	 * 
	 */
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(String name, String netID, String email,
			List<Appointment> appointments) {
		this.name = name;
		this.netID = netID;
		this.email = email;
		this.appointments = appointments;
	}

	public void makeAppointment() {
		
	}
	
	public void cancelAppointment() {
		
	}
	
	public void viewAppointments() {
		
	}
	
	public void checkAvailability() {
		
	}
	
	public void viewExams() {
		
	}

}
