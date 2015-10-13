import java.util.logging.Logger;

/**
 * An instance of this class is created when an Administrator logs into the system. The class
 * provides functions for the Admin to interact with the testing center.
 */

/**
 * @author WdNnD
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
	
	/*
	 * The following 8 functions allow the admin to edit the testing center information.
	 * Values are taken from the interface and passed to the testingCenter to make the change.	 * 
	 */
	public void editNumberSeats(int n) {
		tC.setNumberofSeats(n);
	}
	
	public int viewNumberSeats() {
		return tC.getNumSeats();
	}
	
	public void editSetAside(int n) {
		tC.setSetAside(n);
	}
	
	public int viewSetAside(){
		return tC.getSetAside();
	}
	
	public void editGapTime(int h, int m){
		tC.setGapTime(h, m);
	}
	
	public int viewGapTime() {
		return tC.getGapTime();
	}
	
	public void editReminder(int n) {
		tC.setReminder(n);
	}
	
	public int viewReminder() {
		return tC.getReminder();
	}
	
	/*
	 * Gives the testingCenter the command to update the data in the database.
	 */
	public void importData() {
		tC.updateData();
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
