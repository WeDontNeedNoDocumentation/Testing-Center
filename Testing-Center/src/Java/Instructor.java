package Java;
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
	private String instructorId;
	
	private final TestingCenter tC = TestingCenter.getTestingCenter();
	
	/**
	 * 
	 */
	
	public Instructor(String name, String email, String instructorId) {
		this.name = name;
		this.email = email;
		this.instructorId = instructorId;
	}
	//
	public Instructor(String name, String email,TestingCenter tc, String instructorId) {
		this.name = name;
		this.email = email;
		this.instructorId = instructorId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
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
	
	public void viewAttendanceStats(String examId) {
		List<Student> attendence = tC.viewAttendanceStats(examId);
	}

}
