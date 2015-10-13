import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * 
 */

/**
 * @author Daniel
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
