/*
*	Author: Rhys B
*	Created: 2021-11-07
*	Modified: 2021-11-07
*
*	Contains information for a time in a day.
*/


package tempo;

import java.util.Calendar;


public class Time {
	private int hour, minute, ampm;

	public Time() {
		Calendar cal = Calendar.getInstance();

		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		ampm = cal.get(Calendar.AM_PM);

		if (hour == 0) {
			hour = 12;
		}
	}

	public Time(int hour, int minute, int ampm) {
		this.hour = hour;
		this.minute = minute;
		this.ampm = ampm;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getAmPm() {
		return ampm;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public void setAmPm(int ampm) {
		this.ampm = ampm;
	}
}
