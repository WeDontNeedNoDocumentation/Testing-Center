import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	
	private Database db;
	private TestingCenter tc;
	
	@BeforeClass
	public void beforeClass() {
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();
		
		db.updateQuery("CREATE DATABASE Test");
		db.updateQuery("CREATE TABLE student");
		db.updateQuery("CREATE TABLE test");
		
		db.updateQuery("INSERT INTO student ('Dan', 'Hare', 'dharel', 'dan.harel@stonybrook.edu')");
		db.updateQuery("INSERT INTO exam (1, '', '', '', '', 1, '', ''");
		//db.updateQuery("INSERT INTO appointment (1, 'dharel', 0, 0, 1)");
	}
	
	@Test
	public void testCreateAppointment() {
		Student student = new Student("Dan Harel", "dharel", "dan.harel@stonybrook.edu", null);
		Exam exam = new Exam(1, null, null);
		
		student.makeAppointment(exam, new DateTime(2000,1,1,1,1), 0, 1);
	}

}
