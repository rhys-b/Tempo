/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-14
*
*	GUI dialog for showing information about a goal.
*/


package tempo.goal;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import tempo.Window;

import tempo.goal.GoalConstants;

import tempo.graph.GraphScale;
import tempo.graph.LineGraph;


public class GoalInfoDialog extends JDialog implements ActionListener {
	private static final SimpleDateFormat dateFormat =
					new SimpleDateFormat("MMMM dd, yyyy");

	private Goal goal;

	public GoalInfoDialog(Goal goal) {
		super(Window.getFrame(), goal.getName(), true);

		this.goal = goal;

		setSize(600, 300);
		setLocationRelativeTo(Window.getFrame());

		JLabel label = new JLabel(goal.toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD));

		JButton edit = new JButton("Edit");
		edit.addActionListener(this);

		JLabel statusLabel = new JLabel("Status:");

		JLabel status;
		if (goal.hasData()) {
			if (goal.isOnTrack()) {
				status = new JLabel(GoalConstants.TRACK);
				status.setForeground(GoalConstants.ON_TRACK);
			} else {
				status = new JLabel(GoalConstants.FALLING);
				status.setForeground(
						GoalConstants.FALLING_BEHIND);
			}
		} else {
			status = new JLabel(GoalConstants.NO_DATA);
		}

		JLabel start = new JLabel(
			"Started: " + dateFormat.format(goal.getStart()));
		start.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel end;
		if (goal.getType() == Goals.TYPE_CUMULATIVE) {
			end = new JLabel("Ends: " + dateFormat.format(
						(Date)goal.getTypeData()));
		} else {
			String s = "Repeats ";
			String td = (String)goal.getTypeData();

			if (td.equals("day")) {
				s = "daily";
			} else if (td.equals("week")) {
				s += "weekly";
			} else if (td.equals("month")) {
				s += "monthly";
			} else {
				s += "yearly";
			}

			end = new JLabel(s);
		}
		end.setHorizontalAlignment(SwingConstants.CENTER);

		GraphScale graph = new GraphScale(
				new LineGraph(goal.getGraphData()), false);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
			.addGroup(layout.createSequentialGroup()
				.addComponent(label, 300, 400,Integer.MAX_VALUE)
				.addComponent(edit, 100, 100, 200)
			)
			.addGroup(layout.createSequentialGroup()
				.addComponent(statusLabel, 50, 50, 100)
				.addComponent(status, 100, 150, 175)
				.addComponent(start, 100, 200,Integer.MAX_VALUE)
				.addComponent(end, 100, 200, Integer.MAX_VALUE)
			)
			.addComponent(graph, 100, 500, Integer.MAX_VALUE)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(label)
				.addComponent(edit)
			)
			.addGroup(layout.createParallelGroup()
				.addComponent(statusLabel)
				.addComponent(status)
				.addComponent(start)
				.addComponent(end)
			)
			.addComponent(graph, 150, 150, Integer.MAX_VALUE)
		);

		setLayout(layout);
	}

	public void display() {
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		dispose();

		Goals.showGoalEditDialog(goal);
	}
}
