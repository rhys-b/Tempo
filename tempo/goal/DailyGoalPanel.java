/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	GUI component allowing input from the user regarding one of their goals.
*/


package tempo.goal;

import java.awt.Container;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;

import tempo.goal.Goal;

import tempo.Spinner;


public class DailyGoalPanel extends JPanel implements ActionListener {
	private static int HEIGHT = 40;

	private Goal goal;
	private Spinner spinner;

	public DailyGoalPanel(Goal goal) {
		this.goal = goal;

		JLabel label = new JLabel("How many " + goal.getNoun() +
			" did you " + goal.getAction() + " today?");

		spinner = new Spinner(new SpinnerNumberModel(
			0, 0, Integer.MAX_VALUE, 1.0));

		JButton done;
		try {
			done = new JButton(new ImageIcon(ImageIO.read(
				DailyGoalPanel.class.getResourceAsStream(
					"done.png")).getScaledInstance(
					20, 20,Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			done = new JButton("\u2713");
		}

		done.setToolTipText("Submit");
		done.addActionListener(this);

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(done, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(spinner, 100, 100, 100)
			.addGap(20)
			.addComponent(label, 0, GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(done, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(spinner, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(label, HEIGHT, HEIGHT, HEIGHT)
		);

		setLayout(layout);
	}

	public void actionPerformed(ActionEvent ae) {
		goal.setDailyValue(spinner.getDouble());

		Container parent = getParent();
		parent.remove(this);
		parent.revalidate();
		parent.repaint();
	}
}
