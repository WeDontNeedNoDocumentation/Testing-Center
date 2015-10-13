import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/**
 * 
 */

/**
 * @author Daniel
 *
 */
public class TestingCenter {

	private List<Day> days;
	private int numberOfSeats;
	private int numberOfSetAside;
	private LocalTime open;
	private LocalTime close;
	private Period gap;
	private Period reminderInt;
	private Database db;
	/**
	 * @param db 
	 * 
	 */
	
	public TestingCenter(List<Day> days, int numberOfSeats,
			int numberOfSetAside, LocalTime open, LocalTime close, Period gap,
			Period reminderInt, Database db) {
		this.days = days;
		this.numberOfSeats = numberOfSeats;
		this.numberOfSetAside = numberOfSetAside;
		this.open = open;
		this.close = close;
		this.gap = gap;
		this.reminderInt = reminderInt;
		this.db = db;
	}
	
	public void checkAvailability() {
		
	}
	
	public void makeAppointment() {
		
	}
	
	public void checkAppointment() {
		
	}
	
	public void produceStats() {
		
	}
	
	public void showAppointments() {
		
	}
	
	public void blockDay() {
		
	}
	
	public boolean updateData() {
		ArrayList<String> lines = new ArrayList<String>();
		
		
		try {
			FileReader reader = new FileReader(new File("user.csv"));
			BufferedReader bReader = new BufferedReader(reader);
			String currentLine;
			while ((currentLine = bReader.readLine()) != null){
				lines.add(currentLine);
			}
			bReader.close();
			//db.query("INSERT INTO student VALUES ('Daniel','Myrick',7,'none@wo.com');");
			for(int i = 1; i < lines.size(); i++) {
				
				System.out.println(lines.get(i));
			}
			System.out.println(queryFormat(lines.get(1)));
			return true;
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return false;
		} catch (IOException e) {
			System.out.println("An error occured while reading.");
			return false;
		}
	}
	
	private String queryFormat(String line) {
		String[] words = line.split(",");
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < words.length;i++) {
			sb.append("'");
			sb.append(words[i]);
			sb.append("'");
			if(i != words.length-1){
				sb.append(",");
			}
		}
		return sb.toString();
		
	}
	public void sendNotice() {
		
	}
	
	public void makeReservation() {
		
	}
	
	public void getUpcoming() {
		
	}
	
	public void getInstructorExams() {
		
	}
	
	public void getAdHocExams() {
		
	}

	private class Notifier {
		
		
	}

	public void setNumberofSeats(int n) {
		numberOfSeats = n;
		
	}

	public int getNumSeats() {
		return numberOfSeats;
	}

	public void setSetAside(int n) {
		numberOfSetAside = n;
		
	}
	
	public int getSetAside() {
		return numberOfSetAside;
	}

	public void setGapTime(int h, int m) {
		gap = new Period(h,0,0,0);
		
	}

	public int getGapTime() {
		return gap.getHours();
	}

	public void setReminder(int h) {
		reminderInt = new Period(h,0,0,0);
		
	}

	public int getReminder() {
		return reminderInt.getHours();
		
	}
}
