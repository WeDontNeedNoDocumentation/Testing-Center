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
	public boolean makeExam(Exam exam, DateTime start, DateTime end, boolean courseExam) {
		return tC.makeReservation(exam, start, end, courseExam, this.instructorId);
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
	
	/**
	 * Creates an Ad Hoc Exam
	 * @param termId	Term in which the exam will take place
	 * @param examName	Name of the exam. WIll also be the examId
	 * @param start		Time at which the exam starts
	 * @param end		Time at which the exam ends
	 * @param duration	Duration of the exam in minutes
	 * @param students	List of netIds of the students to take the course 
	 * @return
	 */
	public boolean makeAdHocExam(int termId, String examName, DateTime start, DateTime end, int duration, List<String> students) {
		String queryString = String.format("INSERT INTO course "
				+ "(courseTerm, termId, instructorIdB) "
				+ "VALUES ('%s', %d, '%s')",
				examName,
				termId,
				instructorId);
		Database.getDatabase().updateQuery(queryString);
		
		Exam exam = new Exam(examName, start, end, instructorId, examName, students.size(), duration, true);
		if (makeExam(exam, start, end, false)) {
			enrollStudents(examName, students);
			return true;
		}
		else {
			return false;
		}
	}
	
	private void enrollStudents(String courseId, List<String> students) {
		for (String netId : students) {
			String queryString = String.format("INSERT INTO coursestudent "
					+ "(courseIdCS, studentIdCS) "
					+ "VALUES ('%s', '%s')",
					courseId, netId);
			Database.getDatabase().updateQuery(queryString);
		}
	}
	
	public static void main(String[] args) {
		Instructor inst = new Instructor("Meredith Roberts", "Meredith.Roberts@example.com", "MRoberts");
		
		List<String> netIds = new ArrayList<String>();
		netIds.add("a");
		netIds.add("abak");
		
		//inst.makeAdHocExam(1158, "test-adhoc-exam", new DateTime(2015, 12, 15, 0, 0), new DateTime(2015, 12, 20, 0, 0), 60, netIds);
		
		List<Exam> exams = inst.viewExams();
		for (Exam exam : exams) {
			System.out.println(exam);	
		}
	}
}
