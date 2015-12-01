package Java;
import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;

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
	
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	public Administrator(String name, String email) {
		super();
		this.name = name;
		this.email = email;
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
	public void importData(String studentsFileName, /*String instructorFileName, */String coursesFileName, String rostersFileName) {
		tC.updateData(studentsFileName, /*instructorFileName, */coursesFileName, rostersFileName);
	}
	
	public void displayUtilization() {
		
	}
	
	public List<Exam> viewExams() {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.getAllExams();
	}
	
	public List<Exam> viewPendingExams() {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.getPendingExams();
	}
	
	/**
	 * 
	 * @param examId	ID of the exam
	 * @param newStatus	Must be either "A" for ACCEPT or "D" for DENY
	 */
	public void setExamStatus(String examId, String newStatus) {
		tC.setExamStatus(examId, newStatus);
	}
	
	public void setExamStatus(Exam exam, String newStatus) {
		setExamStatus(exam.getExamID(), newStatus);
	}
	
	public void generateReport() {
		
	}
	
	public void makeAppointment(String examId, DateTime time, int seatId, String netID, DateTime startTime, DateTime endTime, int duration) {
		tC.makeAppointment(examId, time, netID, startTime, endTime, duration);
	}
	
	public void checkAvailability() {
		
	}
	
	public List<Appointment> viewAppointments() {
		return tC.viewAllAppointments();
	}
	
	public void cancelAppointment(int appID) {
		tC.cancelAppointment(appID);
	}
	
	public int checkInStudent(String netID) {
		return tC.checkIn(netID);
	}
}
