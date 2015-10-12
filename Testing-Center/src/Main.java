import java.util.Date;
import java.util.Scanner;



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
		
		TestingCenter tC = new TestingCenter(null,64,0,8,22,1,1);

		boolean running;
		while(running = true)
		System.out.println("Pick a user type:");
		System.out.println("1) Admin 2) Instructor 3) Student 4) close server");
		Scanner s = new Scanner(System.in);
		int option = s.nextInt();
		if(option == 1) {
			Administrator ad = new Administrator("admin","admin@help.edu",tC);
		} else if (option == 2) {
			Instructor ins = new Instructor("teacher","teacher@help.edu",tC);
		} else if (option == 3) {
			Student stu = new Student("Daniel Myrick","dmyrick","daniel.myrick@stonybrook.edu",null);
		} else if (option == 4) {
			running = false;
		}
	}

}
