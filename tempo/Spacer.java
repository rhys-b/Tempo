/*
*	Author: Rhys B
*	Created: 2021-11-07
*	Modified: 2021-11-07
*
*	GUI components that adds empty space, for a box layout.
*/


package tempo;

import java.awt.Dimension;

import javax.swing.JPanel;


public class Spacer extends JPanel {
	public Spacer(int size) {
		setMaximumSize(new Dimension(Integer.MAX_VALUE, size));
	}
}
