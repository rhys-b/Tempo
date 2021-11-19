/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-13
*
*	Contains static methods to help with the calendar GUI component.
*/


package tempo.calendar;

import java.util.Calendar;
import java.util.Date;


public class CalendarUtils {
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getYearOffset(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}
}
