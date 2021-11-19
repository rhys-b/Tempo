/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-13
*
*	GUI component that displays and allows the year
*	to be changed on the calendar.
*/


package tempo.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tempo.calendar.CalendarUtils;
import tempo.calendar.Year;

import tempo.goal.Goal;


public class CalendarView extends JPanel implements	ComponentListener,
							ActionListener
{
	private int year;
	private JLabel label;
	private JButton decrement, increment;
	private Year child;
	private JPanel header;

	public CalendarView() {
		this(new ArrayList<Goal>());
	}

	public CalendarView(ArrayList<Goal> list) {
		this(CalendarUtils.getYear(new Date()), list);
	}

	public CalendarView(int year, ArrayList<Goal> list) {
		child = new Year(year, list);

		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont((float)30));
		label.setFont(label.getFont().deriveFont(Font.BOLD));

		try {
			decrement = new JButton(new ImageIcon(ImageIO.read(
				CalendarView.class.getResourceAsStream(
				"left_arrow.png"))
				.getScaledInstance(20, 20,Image.SCALE_SMOOTH)));
			increment = new JButton(new ImageIcon(ImageIO.read(
				CalendarView.class.getResourceAsStream(
				"right_arrow.png"))
				.getScaledInstance(20, 20,Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			decrement = new JButton("<");
			increment = new JButton(">");
		}

		increment.addActionListener(this);
		decrement.addActionListener(this);

		setYear(year);

		header = new JPanel();
		header.setLayout(null);
		header.addComponentListener(this);
		header.setMinimumSize(new Dimension(1, 50));
		header.setPreferredSize(new Dimension(1, 50));

		header.add(decrement);
		header.add(increment);
		header.add(label);

		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(child);
	}

	public void setYear(int year) {
		this.year = year;

		label.setText(Integer.toString(year));
		child.setYear(year);
	}

	public int getYear() {
		return year;
	}

	public void componentResized(ComponentEvent ce) {
		int w = header.getWidth(), h = header.getHeight();

		label.setBounds((w / 2) - 100, 5, 200, h - 10);
		decrement.setBounds((w / 2) - 95 - h, 5, h - 10, h - 10);
		increment.setBounds((w / 2) + 105, 5, h - 10, h - 10);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource().equals(decrement)) {
			setYear(getYear() - 1);
		} else {
			setYear(getYear() + 1);
		}
	}

	public void setList(ArrayList<Goal> list) {
		child.setList(list);
	}

	public void componentHidden(ComponentEvent ce) {}
	public void componentShown(ComponentEvent ce) {}
	public void componentMoved(ComponentEvent ce) {}
}
