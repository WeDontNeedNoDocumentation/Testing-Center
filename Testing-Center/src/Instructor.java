import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * This class represents and Instructor user and provides functionality for the instructor to interact
 * with the TestingCenter.  In instance of this class will be created on log in with information from
 * the database.
 */

/**
 * @author WdNnD
 *
 */
public class Instructor {

	private String name;
	private String email;
	private TestingCenter tC;
	private String instructorId;
	
	/**
	 * 
	 */
	
	public Instructor(String name, String email, TestingCenter tC, String instructorId) {
		this.name = name;
		this.email = email;
		this.tC = tC;
		this.instructorId = instructorId;
	}
	
	/*
	 * The instructor receives a list of all exams associated with his ID.
	 * (NOTE: The internal functionality may be moved later and called by this function.)
	 */
	public List<Exam> viewExams() {
		return tC.getInstructorExams(this.instructorId);
		
	}
	
	/*
	 * The instructor can make an exam reservation in the database. A query with the relevent information
	 * is created and sent.
	 * (NOTE: This does not yet check for conflicts.)
	 */
	public void makeExam(Exam exam, DateTime start, DateTime end, boolean courseExam) {
		tC.makeReservation(exam, start, end, courseExam, this.instructorId);
	}
	
	public void viewAvailability() {
		
	}
	
	/*
	 * The instructor can cancle any exam reservation he has made.
	 * (NOTE: This does not handle an non-existent ID at this time.)
	 */
	public void cancelExam(String examId) {
		tC.cancelExam(examId, this.instructorId);
	}
	
	public void viewAttendanceStats() {
		
	}

}
