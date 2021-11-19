/*
*	Author: Rhys B
*	Created: 2021-11-14
*	Modified: 2021-11-14
*
*	GUI component for showing a single journal entry.
*/


package tempo.journal;

import java.awt.BorderLayout;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class JournalEntry extends JPanel {
	private Date date;

	public JournalEntry(String string, Date date) {
		this.date = date;

		SimpleDateFormat dateFormat =
					new SimpleDateFormat("MMMM d, yyyy");

		setLayout(new BorderLayout());

		JLabel title = new JLabel(dateFormat.format(date));
		title.setFont(title.getFont().deriveFont((float)20));

		JEditorPane text = new JEditorPane();
		text.setEditable(false);
		//text.setContentType("text");
		text.setText(string);

		add(text);
		add(title, BorderLayout.NORTH);
	}

	public boolean comesAfter(JournalEntry other) {
		return getDate().after(other.getDate());
	}

	public Date getDate() {
		return date;
	}
}
