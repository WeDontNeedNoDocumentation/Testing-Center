import org.joda.time.LocalTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	
	Database db;
	TestingCenter tc;
	
	@BeforeClass
	public void beforeClass() {
		db = Database.getDatabase();
		tc = TestingCenter.getTestingCenter();
	}

}
