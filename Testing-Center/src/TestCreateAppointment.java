import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCreateAppointment {
	
	private static Database db;
	private static TestingCenter tc;

	@BeforeClass
	public static void beforeClass() {
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("USE Test");
		
		db.updateQuery("CREATE TABLE student (firstName varchar(45), lastName varchar(45), studentId varchar(45), email varchar(45))");
		db.updateQuery("CREATE TABLE appointment (examId int, studentIdA varchar(45), dateId int, seatIdA int, appointmentIdA int)");
		
		db.updateQuery("INSERT INTO student VALUES ('Dan', 'Harel', 'dharel', 'dan.harel@stonybrook.edu')");
		//db.updateQuery("INSERT INTO exam VALUES (1, '', '', '', '', 1, '', '')");
		//db.updateQuery("INSERT INTO appointment (1, 'dharel', 0, 0, 1)");
	}
	
	@Test
	public void testCreateAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		Exam exam = new Exam("CSE", null, null);
		
		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
		
		List<Appointment> appts = student.viewAppointments();
		assertEquals(appts.get(0).getNetId(), "dharel");
	}
	
	@Test
	public void testDeleteAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		
		student.cancelAppointment(1);
		
		List<Appointment> appts = student.viewAppointments();
		assertEquals(appts.size(), 0);
	}
	
	@AfterClass
	public static void afterClass() {
		db.updateQuery("DROP DATABASE Test");
	}

}
