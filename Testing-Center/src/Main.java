import java.util.Date;
import java.util.List;
import java.util.Scanner;

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

		Database.createDatabase();
		
		/*
		 * Test that the database actually works
		 */
		Database db = Database.getDatabase();
		List results = db.query("SHOW DATABASES");
		for (Object result : results) {
			System.out.println(result);
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
				Instructor ins = new Instructor("teacher","teacher@help.edu",tC);
			} else if (option == 3) {
				Student stu = new Student("Daniel Myrick","dmyrick","daniel.myrick@stonybrook.edu",null);
			} else if (option == 4) {
				running = false;
			}

		}

	}

}
