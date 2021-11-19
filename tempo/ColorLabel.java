/*
*	Author: Rhys B
*	Created: 2021-11-07
*	Modified: 2021-11-07
*
*	GUI component that shows a rectangular color and a label.
*/


package tempo;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ColorLabel extends JPanel {
	public ColorLabel(String s, Color c) {
		JPanel color = new JPanel();
		color.setBackground(c);

		JLabel label = new JLabel(s);

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(20)
			.addComponent(color)
			.addGap(20)
			.addComponent(label)
			.addGap(20)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(color, 0,
				GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
			.addComponent(label, 0,
				GroupLayout.PREFERRED_SIZE, Integer.MAX_VALUE)
		);

		setLayout(layout);
	}
}
