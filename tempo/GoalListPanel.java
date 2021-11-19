/*
*	Author: Rhys B
*	Created: 2021-11-11
*	Modified: 2021-11-14
*
*	GUI component for showing goals.
*/


package tempo;

import java.awt.Color;

import java.awt.image.BufferedImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

import javax.imageio.ImageIO;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.text.SimpleDateFormat;

import tempo.goal.Goal;
import tempo.goal.GoalConstants;
import tempo.goal.Goals;


public class GoalListPanel extends JButton implements ActionListener {
	private static final int HEIGHT = 30;

	private Goal goal;

	public GoalListPanel(Goal goal) {
		this.goal = goal;

		JLabel title = new JLabel(goal.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel track;
		if (goal.hasData()) {
			if (goal.isOnTrack()) {
				track = new JLabel(GoalConstants.TRACK);
				track.setForeground(GoalConstants.ON_TRACK);
			} else {
				track = new JLabel(GoalConstants.FALLING);
				track.setForeground(
						GoalConstants.FALLING_BEHIND);
			}
		} else {
			track = new JLabel(GoalConstants.NO_DATA);
		}
		track.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel end;
		if (goal.getType() == Goals.TYPE_CUMULATIVE) {
			end = new JLabel(new SimpleDateFormat("MMM dd, yyyy")
					.format((Date)goal.getTypeData()));
		} else {
			end = new JLabel("*Continuous*");
		}
		end.setHorizontalAlignment(SwingConstants.CENTER);

		JButton settings = null;
		try {
			settings = new JButton(new ImageIcon(ImageIO.read(
				GoalListPanel.class.getResourceAsStream(
				"gear.png"))
				.getScaledInstance(
				20, 20, BufferedImage.SCALE_SMOOTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Goals.showGoalEditDialog(goal);
			}
		});

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(title, 0, 100, 5000)
			.addComponent(track, 100, 200, 200)
			.addComponent(end, 100, 200, 200)
			.addComponent(settings, 30, 30, 30)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(title, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(track, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(end, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(settings, HEIGHT, HEIGHT, HEIGHT)
		);

		setLayout(layout);
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		Goals.showGoalInfoDialog(goal);
	}

	public Goal getGoal() {
		return goal;
	}
}
