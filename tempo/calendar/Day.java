/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Odified: 2021-11-13
*
*	GUI component for showing a day in the calendar.
*/


package tempo.calendar;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import tempo.goal.Goal;
import tempo.goal.GoalConstants;
import tempo.goal.Goals;


public class Day extends JComponent implements MouseListener {
	private String day;
	private Goal goal;
	private boolean isToday;

	public Day() {
		this("", null);
	}

	public Day(String day, Goal goal) {
		this.day = day;
		this.goal = goal;

		isToday = false;

		addMouseListener(this);
	}

	public void setDay(String day) {
		this.day = day;
		repaint();
	}

	public void setToday(boolean b) {
		isToday = b;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
		repaint();
	}

	public String getDay() {
		return day;
	}

	public Goal getGoal() {
		return goal;
	}

	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		Color def = gg.getColor();

		int size = Math.min(getWidth(), getHeight());
		int horizontal = (int)Math.round((getWidth() - size) / 2.0);
		int vertical = (int)Math.round((getHeight() - size) / 2.0);

		if (isToday) {
			gg.setColor(new Color(255, 0, 0));
			gg.fillOval(horizontal, vertical, size, size);
			gg.setColor(Color.BLACK);
		} else if (goal != null) {
			if (goal.hasData()) {
				if (goal.isOnTrack()) {
					gg.setColor(GoalConstants.ON_TRACK);
				} else {
					gg.setColor(
						GoalConstants.FALLING_BEHIND);
				}
			} else {
				gg.setColor(def);
			}

			gg.fillOval(horizontal, vertical, size, size);
			gg.setColor(Color.BLACK);
		}

		FontMetrics fm = gg.getFontMetrics();

		gg.drawString(day,
			(int)Math.round((getWidth() / 2.0) -
			(fm.stringWidth(day) / 2.0)),
			(int)Math.round((getHeight() / 2.0) +
			((fm.getAscent() - fm.getDescent()) / 2.0)));
	}

	public void mouseClicked(MouseEvent me) {
		if (goal != null) {
			Goals.showGoalInfoDialog(goal);
		}
	}

	public void mousePressed(MouseEvent me) {}
	public void mouseReleased(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
}
