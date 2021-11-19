/*
*	Author: Rhys B
*	Created: 2021-11-14
*	Modified: 2021-11-14
*
*	GUI component that aligns a button right that when clicked on opens
*	the journal.
*/


package tempo.journal;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import tempo.journal.Journal;


public class JournalButton extends JPanel implements ActionListener {
	public JournalButton() {
		setLayout(new BorderLayout());

		JButton button = new JButton("Journal");
		button.addActionListener(this);

		add(button, BorderLayout.EAST);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
	}

	public void actionPerformed(ActionEvent ae) {
		Journal.openJournal();
	}
}
