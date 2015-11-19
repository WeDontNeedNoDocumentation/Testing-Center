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
		
		inst = new Instructor("Stoller", "stoller@cs.stonybrook.edu", "sstoller");
		st = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("USE Test");

		db.updateQuery("CREATE TABLE student (firstName varchar(45), lastName varchar(45), studentId varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE appointment (examIdA varchar(45), studentIdA varchar(45), dateIdA int, seatIdA int, appointmentId int)");
		db.updateQuery("CREATE TABLE exam (examId varchar(45), start int, end int, boolCourseExam varchar(45), examStatus varchar(45), instructorId varchar(45), numSeats int, examLength int)");
		db.updateQuery("CREATE TABLE instructor (instructorId varchar(45), name varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE courseexam (examIdCE varchar(45), courseIdCE varchar(45))");
		db.updateQuery("CREATE TABLE coursestudent (courseIdCS varchar(45), studentIdCS varchar(45))");
		db.updateQuery("CREATE TABLE course (courseId varchar(45), subject varchar(45), catalogNumber varchar(45), section varchar(45), instructor varchar(45))");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO instructor VALUES ('SStoller', 'Scott Stoller', 'stoller@cs.stonybrook.edu')");
		db.updateQuery("INSERT INTO course VALUES ('81468-1158', 'CSE', '308', '01', 'SStoller')");
		db.updateQuery("INSERT INTO course VALUES ('80450-1158', 'CSE', '373', '01', 'SSkiena')");		
		db.updateQuery("INSERT INTO exam VALUES ('exam1', 0, 0, '1', 'A', 'SStoller', 64, 60)");
		db.updateQuery("INSERT INTO exam VALUES ('exam2', 0, 0, '1', 'A', 'SStoller', 64, 60)");
		db.updateQuery("INSERT INTO exam VALUES ('exam3', 0, 0, '1', 'A', 'SSkiena', 64, 60)");
		db.updateQuery("INSERT INTO courseexam VALUES ('exam1', '81468-1158')");
		db.updateQuery("INSERT INTO courseexam VALUES ('exam2', '81468-1158')");
		db.updateQuery("INSERT INTO courseexam VALUES ('exam3', '80450-1158')");
		db.updateQuery("INSERT INTO coursestudent VALUES ('81468-1158', 'dharel')");
		db.updateQuery("INSERT INTO coursestudent VALUES ('80450-1158', 'dharel')");

	}
	
	@Test
	public void AtestStudentCreateAppointment() {
		logger.info("Testing Student's ability to create an appointment.");
		
		Exam exam = new Exam("CSE", null, null, "SStoller", 64, 60);
		
		List<Appointment> appts;
		
		appts = st.viewAppointments();
		int startSize = appts.size();
		
		st.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
		
		appts = st.viewAppointments();
		int endSize = appts.size();
		
		assertEquals(1, endSize - startSize);
	}
	

	public void frontAtestStudentCreateAppointment(String examId, String studentIdA, int month, int day, int hour, int seatIdA, int appointmentId, String instructorId ) {
		Exam exam = new Exam(examId, null, null, instructorId, 64, 60);
		
		List<Appointment> appts;
		
		appts = st.viewAppointments();
	//	int startSize = appts.size();
		
		DateTime time = new DateTime(2000,month,day,hour,1);
		
		st.makeAppointment(exam, time, seatIdA, appointmentId);
		
	//	appts = st.viewAppointments();
	//	int endSize = appts.size();
		
		//assertEquals(1, endSize - startSize);
	}
	
	public void frontAtestStudentViewAppointments(){
		st.viewAppointments();
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
	
	@Test
	public void CtestInstructorCreateAppointment() {
		logger.info("Testing Instructor's ability to create an exam scheduling request.");
		
		Instructor inst = new Instructor("Scott Stollerd", "stollerd@cs.stonybrook.edu", "SStollerd");
		List<Exam> exams;
		
		exams = inst.viewExams();
		int startNumExams = exams.size();
		
		Exam exam = new CourseExam("CSE", null, null, null, "sstollerd", "P", 64,2);
		
		inst.makeExam(exam, new DateTime(2000,1,1,1,1), new DateTime(2000,1,1,1,2), true);
		
		exams = inst.viewExams();
		int endNumExams = exams.size();
		
		assertEquals(1, endNumExams - startNumExams);
		//assertEquals("CSE", exams.get(0).getExamID());
	}
	
	/*
	@Test
	public void DtestViewPendingExams() {
		logger.info("Testing Instructor's ability to view exam scheduling request.");
		
		Administrator admin = new Administrator(null, null, null);
		
		List<Exam> exams = admin.viewPendingExams();
		assertEquals(1, exams.size());
	}
	*/
	
	@Test
	public void EtestAcceptExam() {
		logger.info("Testing Admin's ability to accept an exam scheduling request.");
		
		Administrator admin = new Administrator(null, null);
		List<Exam> exams;
		
		exams = admin.viewPendingExams();
		int startNumExams = exams.size();
		
		admin.setExamStatus("CSE", "A");
		
		exams = admin.viewPendingExams();
		int endNumExams = exams.size();
		
		assertEquals(-1, endNumExams - startNumExams);
	}
	
	@Test
	public void FtestInstructorCancelExam() {
		logger.info("Testing Instructor's ability to reject an exam scheduling request.");
		
		List<Exam> exams;
		
		exams = inst.viewExams();
		int startNumExams = exams.size();
		
		inst.cancelExam("CSE");
		
		exams = inst.viewExams();
		int endNumExams = exams.size();
		
		assertEquals(0, endNumExams - startNumExams);
	}
	
	@Test
	public void GtestStudentAvailableExams() {
		logger.info("Testing Student's ability to get exams they can make an appointment for.");
		
		List<Exam> exams = st.viewExams();
		
		assertEquals(3, exams.size());
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
