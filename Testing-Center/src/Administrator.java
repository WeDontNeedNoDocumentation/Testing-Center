/**
 * 
 */

/**
 * @author danharel
 *
 */
public class Administrator {
	private String name;
	private String email;
	private TestingCenter tC;
	
	public Administrator(String name, String email, TestingCenter tC) {
		super();
		this.name = name;
		this.email = email;
		this.tC = tC;
	}
	
	public void editNumberSeats(int n) {
		tC.setNumberofSeats(n);
	}
	
	public void editOtherFields() {
		
	}
	
	public void importData() {
		
	}
	
	public void displayUtilization() {
		
	}
	
	public void viewExams() {
		
	}
	
	public void approveDenyExam() {
		
	}
	
	public void generateReport() {
		
	}
	
	public void makeAppointment() {
		
	}
	
	public void checkAvailability() {
		
	}
	
	public void viewAppointments() {
		
	}
	
	public void cancelAppointment() {
		
	}
	
	public void checkInStudent() {
		
	}
}
