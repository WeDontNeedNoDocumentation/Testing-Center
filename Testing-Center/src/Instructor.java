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
		List<Exam> exams = new ArrayList<Exam>();
		String queryString = String.format("SELECT * FROM exam "
				+ "INNER JOIN instructor ON exam.instructorId=instructor.instructorId"
				);
		Database db = Database.getDatabase();
		List<Map<String,Object>> examList = db.query(queryString);
		for (Map<String,Object> exam : examList) {
			System.out.println(exam);
			String examId = (String) exam.get("examId");
			DateTime start = new DateTime((int) exam.get("start")*1000);
			DateTime end = new DateTime((int) exam.get("end")*1000);
			
			Exam newExam = new Exam(examId, start, end);
			exams.add(newExam);
		}
		
		return exams;
	}
	
	/*
	 * The instructor can make an exam reservation in the database. A query with the relevent information
	 * is created and sent.
	 * (NOTE: The internal functionality may be moved later and called by this function.)
	 * (NOTE: This does not yet check for conflicts.)
	 */
	public void makeExam(Exam exam, DateTime start, DateTime end, boolean courseExam, String status) {
		String queryString = String.format("INSERT INTO exam VALUES ("
				+ "'%s', %d, %d, %d, '%s', '%s')", 
				exam.getExamID(), 
				start.getMillis()/1000,
				end.getMillis()/1000,
				courseExam ? 0 : 1,
				status,
				this.instructorId
				);
		Database db = Database.getDatabase();
		db.updateQuery(queryString);
	}
	
	public void viewAvailability() {
		
	}
	
	/*
	 * The instructor can cancle any exam reservation he has made.
	 * (NOTE: The internal functionality may be moved later and called by this function.)
	 * (NOTE: This does not handle an non-existent ID at this time.)
	 */
	public void cancelExam(String examId) {
		String queryString = String.format("DELETE FROM exam"
				+ " WHERE "
				+ "instructorId='%s'"
				+ " AND "
				+ "examId='%s'",
				this.instructorId,
				examId
				);
		System.out.println(queryString);
		Database db = Database.getDatabase();
		db.updateQuery(queryString);
	}
	
	public void viewAttendanceStats() {
		
	}

}
