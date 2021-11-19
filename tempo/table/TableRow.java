/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-12
*
*	GUI component for each row of a table.
*/


package tempo.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import javax.imageio.ImageIO;

import tempo.GoalListPanel;

import tempo.goal.Goals;


public class TableRow extends JPanel implements ActionListener {
	public static final int HEIGHT = 40;
	private static ImageIcon closeImage = generateCloseImage();

	private JComponent child;
	private boolean openClose;

	public TableRow(JComponent child) {
		this(child, false);
	}

	public TableRow(JComponent child, boolean openClose) {
		// Gotta do this because 'cannot reference this before
		// supertype constructor has been called'

		this.openClose = openClose;
		this.child = child;

		JButton close;
		if (closeImage == null) {
			close = new JButton("X");
		} else {
			close = new JButton(closeImage);
		}
		close.addActionListener(this);

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(child)
			.addComponent(close, HEIGHT, HEIGHT, HEIGHT)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(child, HEIGHT, HEIGHT, HEIGHT)
			.addComponent(close, HEIGHT, HEIGHT, HEIGHT)
		);

		setLayout(layout);
	}

	private static ImageIcon generateCloseImage() {
		try {
			return new ImageIcon(ImageIO.read(
				TableRow.class.getResourceAsStream("close.png"))
					.getScaledInstance(
					20, 20, Image.SCALE_SMOOTH));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (openClose) {
			Goals.showDeleteDialog(
					((GoalListPanel)child).getGoal());
		} else {
			Container parent = getParent();

			parent.remove(this);
			parent.revalidate();
			parent.repaint();
		}
	}

	public JComponent getChild() {
		return child;
	}
}
