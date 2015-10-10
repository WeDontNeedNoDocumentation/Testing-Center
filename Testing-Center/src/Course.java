import java.util.List;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class Course {

	private String courseID;
	private List<Exam> exams;
	private List<Instructor> instructors;
	private List<Student> students;
	/**
	 * 
	 */
	public Course() {
		// TODO Auto-generated constructor stub
	}
	
	public Course(String courseID, List<Exam> exams,
			List<Instructor> instructors, List<Student> students) {
		this.courseID = courseID;
		this.exams = exams;
		this.instructors = instructors;
		this.students = students;
	}

}
