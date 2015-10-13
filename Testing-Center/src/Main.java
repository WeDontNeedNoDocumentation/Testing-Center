import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;



/**
 * This is the master class that creates the testingCenter and the DB class. 
 * For the time being it is also serving as the base of our text interface in the absence of a GUI.
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

		Database.createDatabase();
		
		/*
		 * Test that the database actually works
		 */
		Database db = Database.getDatabase();
		List<Map<String,Object>> results = db.query("SHOW DATABASES");
		for (Map<String, Object> map : results) {
			for (Object name : map.values()) {
				System.out.println(name);
			}
		}
		

		TestingCenter tC = new TestingCenter(
				null,
				64,
				0,
				new LocalTime(8,0),
				new LocalTime(8,0),
				new Period(1,0,0,0),
				new Period(1,0,0,0)
				);

		boolean running = true;
		while(running) {
			System.out.println("Pick a user type:");
			System.out.println("1) Admin 2) Instructor 3) Student 4) close server");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();
			if(option == 1) {
				Administrator ad = new Administrator("admin","admin@help.edu",tC);
				//System.out.println("New number of Seats:");
				//ad.editNumberSeats( s.nextInt());
				//System.out.print("There are now ");
				//System.out.print(ad.viewNumberSeats());
				//System.out.println(" seats");
				ad.importData();
			} else if (option == 2) {
				Instructor ins = new Instructor("teacher","teacher@help.edu",tC,"teacher");
				//ins.makeExam(null,new DateTime(2000,1,1,8,0),new DateTime(2000,10,1,9,30));
			} else if (option == 3) {
				Student stu = new Student("Daniel Myrick","dmyrick","daniel.myrick@stonybrook.edu",null);
			} else if (option == 4) {
				running = false;
			}

		}

	}

}
