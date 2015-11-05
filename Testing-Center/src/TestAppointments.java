import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestAppointments {
	
	private static final Logger logger = Logger.getLogger(TestAppointments.class.getName());
	
	private static Database db;
	private static TestingCenter tc;
	
	private static Instructor inst;
	private static Student st;

	@BeforeClass
	public static void beforeClass() {
		logger.info("Preparing dummy database for testing.");		
		logger.info("Testing consecutive statements.");
		
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();

		inst = new Instructor("Stoller", "stoller@cs.stonybrook.edu", tc, "sstoller");
		st = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("USE Test");

		db.updateQuery("CREATE TABLE student (firstName varchar(45), lastName varchar(45), studentId varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE appointment (examId varchar(45), studentIdA varchar(45), dateIdA int, seatIdA int, appointmentId int)");
		db.updateQuery("CREATE TABLE exam (examId varchar(45), start int, end int, boolCourseExam varchar(45), examStatus varchar(45), instructorId varchar(45), numSeats int)");
		db.updateQuery("CREATE TABLE instructor (instructorId varchar(45), name varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE courseexam (examIdCE varchar(45), courseIdCE varchar(45))");
		db.updateQuery("CREATE TABLE coursestudent (courseIdCS varchar(45), studentIdCS varchar(45))");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO instructor VALUES ('sstoller', 'Scott Stoller', 'stoller@cs.stonybrook.edu')");
		db.updateQuery("INSERT INTO exam VALUES ('exam1', 0, 0, '1', 'A', 'SStoller', 64");
		db.updateQuery("INSERT INTO exam VALUES ('exam2', 0, 0, '1', 'A', 'SStoller', 64");
		db.updateQuery("INSERT INTO courseexam VALUES ('exam1', 0, 0, '1', 'A', 'SStoller', 64");

	}
	
	@Test
	public void AtestStudentCreateAppointment() {
		logger.info("Testing Student's ability to create an appointment.");
		
		Exam exam = new Exam("CSE", null, null, "sstoller", 64, 0);
		
		List<Appointment> appts;
		
		appts = st.viewAppointments();
		int startSize = appts.size();
		
		st.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
		
		appts = st.viewAppointments();
		int endSize = appts.size();
		
		assertEquals(1, endSize - startSize);
	}
	
	@Test
	public void BtestStudentDeleteAppointment() {
		logger.info("Testing Student's ability to delete an appointment.");
		
		List<Appointment> appts;
		
		appts = st.viewAppointments();
		int startSize = appts.size();
		
		st.cancelAppointment(1);
		
		appts = st.viewAppointments();
		int endSize = appts.size();
		
		assertEquals(-1, endSize - startSize);
	}
	
	// Not entirely sure how to test this one properly....
	/*
	@Test
	public void CtestInstructorCreateAppointment() {
		logger.info("Testing Instructor's ability to create an exam scheduling request.");
		
		Instructor inst = new Instructor("Scott Stollerd", "stollerd@cs.stonybrook.edu", tc, "SStollerd");
		Exam exam = new OutsideExam("CSE", null, null, null, "sstollerd", 64,2);
		
		inst.makeExam(exam, new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true);
		
		List<Exam> exams = inst.viewExams();
		assertEquals("CSE", exams.get(0).getExamID());
	}
	*/
	
	@Test
	public void DtestViewPendingExams() {
		logger.info("Testing Instructor's ability to view exam scheduling request.");
		
		Administrator admin = new Administrator(null, null, null);
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(1, exams.size());
	}
	
	@Test
	public void EtestAcceptExam() {
		logger.info("Testing Admin's ability to accept an exam scheduling request.");
		
		Administrator admin = new Administrator(null, null, null);
		admin.approveDenyExam("CSE", "A");
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(0, exams.size());
	}
	
	@Test
	public void FtestInstructorCancelExam() {
		logger.info("Testing Admin's ability to reject an exam scheduling request.");
		
		inst.cancelExam("CSE");
		
		List<Exam> exams = inst.viewExams();
		assertEquals(0, exams.size());
	}
	
	@Test
	public void GtestStudentAvailableExams() {
		logger.info("Testing Student's ability to get exams they can make an appointment for.");
		
		List<Exam> exams = st.viewExams();
		
		
	}
	
//	@Test
//	public void testNotifierThread(){
//		logger.info("Testing the existance of one thread for the notifier");
//	}
	
	@Test
	public void testCheckIn() {
		logger.info("Testing ability to check student in.");
		assertTrue(tc.checkIn("dhareld")>0);
	}
	
//	@Test
//	public void testGetUpcoming(){
//		Student student = new Student("Safa Sattar", "ssattar", "safa.sattar@stonybrook.edu", null);
//		Exam exam = new Exam("CSE", null, null, "sstoller", 64);
//		
//		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 1, 1);
//		
//		tc.getUpcoming();
//		assertEquals();
//	}
	
	@AfterClass
	public static void afterClass() {
		logger.info("Testing complete.");
		logger.info("Tearing down dummy database.");
		
		db.updateQuery("DROP DATABASE Test");
	}

}
