package Java;
import java.util.List;

/**
 * This is class contains the information about a course.  An object is created and filled
 * when course infromation is requested from the database.
 */

/**
 * @author WdNnD
 *
 */
public class Course {

	private String courseID;
	private String subject;
	private int catalogNumber;
	private String section;
	private String instructorId;
	private int termId;
	private String courseTerm;
	
	public Course(String courseID, String subject, int catalogNumber, String section, String instructorId,
			int termId, String courseTerm) {
		super();
		this.courseID = courseID;
		this.subject = subject;
		this.catalogNumber = catalogNumber;
		this.section = section;
		this.instructorId = instructorId;
		this.termId = termId;
		this.courseTerm = courseTerm;
	}
	
	public String toString() {
		return String.format("{courseId:%s, "
				+ "subject:%s, "
				+ "catalogNumber:%d, "
				+ "section:%s, "
				+ "instructorId:%s, "
				+ "termId:%d, "
				+ "courseTerm:%s}", 
				courseID,
				subject,
				catalogNumber,
				section,
				instructorId,
				termId,
				courseTerm);
	}

	public String getCourseTerm() {
		return courseTerm;
	}
	
	public String getInstructor() {
		return instructorId;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getCatalogNumber() {
		return catalogNumber;
	}

	public void setCatalogNumber(int catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	public int getTermId() {
		return termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}

}
