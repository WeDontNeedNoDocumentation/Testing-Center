import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
		db.updateQuery("CREATE TABLE appointment (examId varchar(45), studentIdA varchar(45), dateIdA int, seatIdA int, appointmentId int)");
		db.updateQuery("CREATE TABLE exam (examId varchar(45), start int, end int, boolCourseExam varchar(45), examStatus varchar(45), instructorId varchar(45), numSeats int)");
		db.updateQuery("CREATE TABLE instructor (instructorId varchar(45), name varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE courseexam (examIdCE varchar(45), courseIdCE varchar(45))");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO instructor VALUES ('sstoller', 'Scott Stoller', 'stoller@cs.stonybrook.edu')");
	}
	
	@Test
	public void AtestStudentCreateAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		Exam exam = new Exam("CSE", null, null, "sstoller", 64);
		
		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
		
		List<Appointment> appts = student.viewAppointments();
		assertEquals("dharel", appts.get(0).getNetId());
	}
	
	@Test
	public void BtestStudentDeleteAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		
		student.cancelAppointment(1);
		
		List<Appointment> appts = student.viewAppointments();
		for (Appointment appt : appts) {
			System.out.println(appt.getNetId());
		}
		assertEquals(0, appts.size());
	}
	
	@Test
	public void CtestInstructorCreateAppointment() {
		Instructor inst = new Instructor("Scott Stoller", "stoller@cs.stonybrook.edu", tc, "SStoller");
		Exam exam = new OutsideExam("CSE", null, null, "sstoller", 64);
		
		inst.makeExam(exam, new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true);
		
		List<Exam> exams = inst.viewExams();
		assertEquals("CSE", exams.get(0).getExamID());
	}
	
	@Test
	public void DtestViewPendingExams() {
		Administrator admin = new Administrator(null, null, null);
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(1, exams.size());
	}
	
	@Test
	public void EtestAcceptExam() {
		Administrator admin = new Administrator(null, null, null);
		admin.approveDenyExam("CSE", "A");
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(0, exams.size());
	}
	
	@Test
	public void FtestInstructorCancelExam() {
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
