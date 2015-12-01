package Java;

import org.joda.time.DateTime;

public class DateTimeUtil 
{
	public DateTimeUtil(){}
	
	public DateTime makeDateTime(String date, String time)
	{
		int month = Integer.parseInt(date.substring(0,2));
		int day = Integer.parseInt(date.substring(3,5));
		int year = Integer.parseInt(date.substring(6,10));
		
		int hour = Integer.parseInt(time.substring(0,2));
		int minute = Integer.parseInt(time.substring(3,5));
		String amPm = time.substring(5,7);
		
		if((amPm.equals("PM")||amPm.equals("pm")||amPm.equals("Pm")) && hour != 12)
		{
			hour+= 12;
		}
		else if((amPm.equals("AM")||amPm.equals("am")||amPm.equals("Am")) && hour == 12)
			hour = 0;
		
		DateTime dateTime = new DateTime(year, month, day, hour, minute, 0, 0);
		
		return dateTime;
	}
}
