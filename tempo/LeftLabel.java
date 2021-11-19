/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	Places a label that aligns left in a box layout.
*/


package tempo;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LeftLabel extends JPanel {
	public LeftLabel(String string) {
		this(string, false, 18, 10);
	}

	public LeftLabel(String string, boolean bold, int size, int margin) {
		JLabel label = new JLabel(string);

		if (bold) {
			label.setFont(label.getFont().deriveFont(Font.BOLD)
				.deriveFont((float)size));
		} else {
			label.setFont(label.getFont().deriveFont((float)size));
		}

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(margin)
			.addComponent(label, 0, GroupLayout.PREFERRED_SIZE,
				Integer.MAX_VALUE)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(label)
		);

		setLayout(layout);
	}
}
