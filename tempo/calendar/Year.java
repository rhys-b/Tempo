/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-13
*
*	GUI component showing an entire year in the calendar.
*/


package tempo.calendar;

import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;

import tempo.calendar.CalendarUtils;
import tempo.calendar.Month;

import tempo.goal.Goal;
import tempo.goal.Goals;


public class Year extends JPanel {
	private int year;
	private Month[] months;
	private ArrayList<Goal> list;

	public Year() {
		this(new ArrayList<Goal>());
	}

	public Year(ArrayList<Goal> list) {
		this(CalendarUtils.getYear(new Date()), list);
	}

	public Year(int year, ArrayList<Goal> list) {
		setLayout(new GridLayout(3, 4, 15, 10));

		createMonths();

		setList(list);
		setYear(year);
	}

	public void setList(ArrayList<Goal> list) {
		this.list = list;

		for (int i = 0; i < months.length; i++) {
			ArrayList<Goal> sublist = new ArrayList<Goal>();

			for (int j = 0; j < list.size(); j++) {
				Goal g = list.get(j);

				if (	g.getType() == Goals.TYPE_CUMULATIVE &&
					CalendarUtils.getYear(
					(Date)g.getTypeData()) == getYear() &&
					CalendarUtils.getMonth(
					(Date)g.getTypeData()) == i)
				{
					sublist.add(g);
				}
			}

			months[i].setList(sublist);
		}
	}

	public void setYear(int year) {
		this.year = year;

		adjustMonths();
	}

	public ArrayList<Goal> getList() {
		return list;
	}

	public int getYear() {
		return year;
	}

	public void adjustMonths() {
		int[] lengths = {
			31,
			isLeapYear(getYear()) ? 29 : 28,
			31,
			30,
			31,
			30,
			31,
			31,
			30,
			31,
			30,
			31
		};

		int offset = CalendarUtils.getYearOffset(getYear()) - 1;

		Date d = new Date();
		boolean isCurrentYear = CalendarUtils.getYear(d) == year;
		int currentMonth = CalendarUtils.getMonth(d);

		for (int i = 0; i < months.length; i++) {
			months[i].adjustDays(offset, lengths[i],
					currentMonth == i && isCurrentYear);
			offset = (offset + lengths[i]) % 7;
		}

		setList(list);
	}

	public boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			if (year % 100 == 0 && year % 400 != 0) {
				return false;
			}

			return true;
		}

		return false;
	}

	private void createMonths() {
		String[] names = {
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"
		};

		months = new Month[12];
		for (int i = 0; i < months.length; i++) {
			months[i] = new Month();
			months[i].setMonth(names[i]);
			add(months[i]);
		}
	}
}
