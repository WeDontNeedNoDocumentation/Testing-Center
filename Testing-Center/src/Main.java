import java.util.Date;

import org.joda.time.LocalTime;
import org.joda.time.Period;



/**
 * 
 */

/**
 * @author WdNnD
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestingCenter tC = new TestingCenter(
				null,
				64,
				0,
				new LocalTime(8,0),
				new LocalTime(8,0),
				new Period(1,0,0,0),
				new Period(1,0,0,0)
				);
		Administrator ad = new Administrator("admin","admin@help.edu",tC);
	}

}
