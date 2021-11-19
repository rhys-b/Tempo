/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-13
*
*	GUI component for the calendar, which displays a month.
*/


package tempo.calendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import tempo.calendar.CalendarUtils;
import tempo.calendar.Day;

import tempo.goal.Goal;
import tempo.goal.Goals;


public class Month extends JPanel {
	private ArrayList<Goal> list;
	private Day[] days;
	private int offset, length;
	private JPanel dayPanel;
	private JLabel title;

	public Month() {
		this("", new ArrayList<Goal>(), 0, 31, false);
	}

	public Month(	String month,
			ArrayList<Goal> list,
			int offset,
			int length,
			boolean containsToday)
	{
		setList(list);

		createDays();
		adjustDays(offset, length, containsToday);

		title = new JLabel();
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD));

		setMonth(month);
		setLayout(new BorderLayout());
		add(dayPanel);
		add(title, BorderLayout.NORTH);
	}

	public void setMonth(String month) {
		title.setText(month);
	}

	public String getMonth() {
		return title.getText();
	}

	public void setList(ArrayList<Goal> list) {
		this.list = list;

		if (days != null) {
			for (int i = 0; i < days.length; i++) {
				days[i].setGoal(null);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getType() == Goals.TYPE_CUMULATIVE) {
				int day = CalendarUtils.getDay((Date)list.get(i)
								.getTypeData());

				days[day + offset - 1].setGoal(list.get(i));
			}
		}
	}

	public ArrayList<Goal> getList() {
		return list;
	}

	public void adjustDays(int offset, int length, boolean containsToday) {
		this.offset = offset;
		this.length = length;

		int today = CalendarUtils.getDay(new Date());

		for (int i = 0; i < offset; i++) {
			days[i].setDay("");
			days[i].setToday(false);
		}

		for (int i = 0; i < length; i++) {
			days[i + offset].setDay(Integer.toString(i + 1));
			days[i + offset].setToday(containsToday &&today==(i+1));
		}

		for (int i = offset + length; i < days.length; i++) {
			days[i].setDay("");
			days[i].setToday(false);
		}
	}

	private void createDays() {
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(6, 7));

		days = new Day[42];
		for (int i = 0; i < days.length; i++) {
			days[i] = new Day();
			dayPanel.add(days[i]);
		}
	}
}
