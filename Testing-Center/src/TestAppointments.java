import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAppointments {
	
	private static Database db;
	private static TestingCenter tc;

	@BeforeClass
	public static void beforeClass() {
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("USE Test");
		
		db.updateQuery("CREATE TABLE student (firstName varchar(45), lastName varchar(45), studentId varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE appointment (examId varchar(45), studentIdA varchar(45), dateIdA int, seatIdA int, appointmentIdA int)");
		db.updateQuery("CREATE TABLE exam (examId varchar(45), start int, end int, boolCourseExam varchar(45), examStatus varchar(45), instructorId varchar(45))");
		db.updateQuery("CREATE TABLE instructor (instructorId varchar(45), name varchar(45), email varchar(45))");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO instructor VALUES ('sstoller', 'Scott Stoller', 'stoller@cs.stonybrook.edu')");
		//db.updateQuery("INSERT INTO exam VALUES (1, '', '', '', '', 1, '', '')");
		//db.updateQuery("INSERT INTO appointment (1, 'dharel', 0, 0, 1)");
	}
	
	@Test
	public void testStudentCreateAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		Exam exam = new Exam("CSE", null, null);
		
		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
		
		List<Appointment> appts = student.viewAppointments();
		assertEquals("dharel", appts.get(0).getNetId());
	}
	
	@Test
	public void testStudentDeleteAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		
		student.cancelAppointment("CSE");
		
		List<Appointment> appts = student.viewAppointments();
		assertEquals(0, appts.size());
	}
	
	@Test
	public void testInstructorCreateAppointment() {
		Instructor inst = new Instructor("Scott Stoller", "stoller@cs.stonybrook.edu", tc, "sstoller");
		Exam exam = new Exam("CSE", null, null);
		
		inst.makeExam(exam, new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true, "PENDING");
		
		List<Exam> exams = inst.viewExams();
		assertEquals("CSE", exams.get(0).getExamID());
	}
	
	@Test
	public void testInstructorCancelExam() {
		Instructor inst = new Instructor("Stoller", "stoller@cs.stonybrook.edu", tc, "sstoller");
		
		inst.cancelExam("CSE");
		
		List<Exam> exams = inst.viewExams();
		assertEquals(0, exams.size());
	}
	
	@AfterClass
	public static void afterClass() {
		db.updateQuery("DROP DATABASE Test");
	}

}
