package Java;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * An instance of this class is created when an Administrator logs into the system. The class
 * provides functions for the Admin to interact with the testing center.
 */

/**
 * Instance of an Administrator in the system
 * @author WdNnD
 *
 */
public class Administrator {
	/** Admin's name */
	private String name;
	/** Admin's email address */
	private String email;
	
	/** Instance TestingCenter to interface with the database */
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	/**
	 * 
	 * @param name		Admin's name
	 * @param email		Admin's email address
	 */
	public Administrator(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	/*
	 * The following 8 functions allow the admin to edit the testing center information.
	 * Values are taken from the interface and passed to the testingCenter to make the change.	 * 
	 */
	/**
	 * Edits the number of seats in the Testing Center
	 * @param n		New number of seats
	 */
	public void editNumberSeats(int n) {
		tC.setNumberofSeats(n);
	}
	
	/**
	 * 
	 * @return The number of seats currently available in the Testing Center
	 */
	public int viewNumberSeats() {
		return tC.getNumSeats();
	}
	
	/**
	 * Sets aside seats in the Testing Center for walk-ins
	 * @param n	
	 */
	public void editSetAside(int n) {
		tC.setSetAside(n);
	}
	
	/**
	 * 
	 * @return The number of set-aside seats in the Testing Center
	 */
	public int viewSetAside(){
		return tC.getSetAside();
	}
	
	/**
	 * Changes the gap time in the testing center
	 * @param h		Number of hours
	 * @param m		Number of minutes
	 */
	public void editGapTime(int h, int m){
		tC.setGapTime(h, m);
	}
	
	/**
	 * 
	 * @return	TestingCenter gap time in minutes
	 */
	public int viewGapTime() {
		return tC.getGapTime();
	}
	
	/**
	 * Changes the reminder time
	 * @param n	Number of minutes before an exam that a student should
	 * 		receive a reminder
	 */
	public void editReminder(int n) {
		tC.setReminder(n);
	}
	
	/**
	 * 
	 * @return	The reminder time of the Testing Center
	 */
	public int viewReminder() {
		return tC.getReminder();
	}
	
	/**
	 * Gives the testingCenter the command to update the data in the database.
	 * @param studentsFileName
	 * @param coursesFileName
	 * @param rostersFileName
	 */
	public void importData(String studentsFileName, /*String instructorFileName, */String coursesFileName, String rostersFileName) {
		tC.updateData(studentsFileName, /*instructorFileName, */coursesFileName, rostersFileName);
	}
	
	/**
	 * Displays the actual utilization of the TestingCenter per day
	 * 	assuming that all pending exams are accepted, for all days in the 
	 * 	specified range.
	 * @param start	Start date
	 * @param end	End date
	 * @return		Map of each date in the given range to its utilization on
	 * 					that date.
	 */
	public Map<LocalDate, Double> displayUtilization(LocalDate start, LocalDate end) {
		return tC.actualUtilizationPerDay(start, end);
	}
	
	/**
	 * This function is not needed AFAIK
	 * @return	All exams in the database.
	 */
	public List<Exam> viewExams() {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.getAllExams();
	}
	
	/**
	 * 
	 * @param term	The current term
	 * @return		All pending exam scheduling requests in the current or a
	 * 					future term, AKA a term >= the current term.
	 */
	public List<Exam> viewPendingExams(int term) {
		TestingCenter tc = TestingCenter.getTestingCenter();
		return tc.getPendingExams(term);
	}
	
	/**
	 * 
	 * @param examId	ID of the exam
	 * @param newStatus	Must be either "A" for ACCEPT or "D" for DENY
	 */
	public void setExamStatus(String examId, String newStatus) {
		if (!newStatus.equals("A") && !newStatus.equals("D"))
			throw new IllegalArgumentException();
		tC.setExamStatus(examId, newStatus);
	}
	
	public void generateReport() {
		
	}
	
	/**
	 * Makes an appointment on behalf of a student
	 * @param examId	ID of the exam
	 * @param time		???
	 * @param netID		NetID of the student
	 * @param startTime	Time at which the appointment starts
	 * @param endTime	Time at which the appointment ends
	 * @param duration	Duration of the appointment
	 */
	public boolean makeAppointment(String examId, DateTime time, String netID, DateTime startTime, DateTime endTime, int duration) {
		return tC.makeAppointment(examId, time, netID, startTime, endTime, duration);
	}
	
	public void checkAvailability() {
		
	}
	
	/**
	 * 
	 * @return	All appointments 
	 */
	public List<Appointment> viewAppointments() {
		return tC.viewAllAppointments();
	}
	
	/**
	 * Cancel an appointment
	 * @param appID	Appointment ID of the exam to cancel
	 */
	public void cancelAppointment(int appID) {
		tC.cancelAppointment(appID);
	}
	
	/**
	 * Checks in a student for its upcoming exam.
	 * @param netID	Net ID of the student being checked in.
	 * @return		The seat number of the appointment.
	 */
	public int checkInStudent(String netID) {
		return tC.checkIn(netID);
	}
}
