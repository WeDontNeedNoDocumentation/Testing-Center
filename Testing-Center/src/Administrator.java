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
