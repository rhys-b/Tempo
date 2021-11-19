/*
*	Author: Rhys B
*	Created: 2021-11-14
*	Modified: 2021-11-14
*
*	GUI dialog that displays the user's past inputs to the 'what was the
*	best part of the day' box.
*/


package tempo.journal;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import tempo.FileManager;
import tempo.Window;

import tempo.journal.JournalEntry;


public class Journal {
	public static void openJournal() {
		JDialog dialog =new JDialog(Window.getFrame(), "Journal",false);
		dialog.setSize(500, 700);
		dialog.setLocationRelativeTo(Window.getFrame());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JournalEntry[] entries = FileManager.loadJournal();
		sort(entries);
		for (int i = 0; i < entries.length; i++) {
			panel.add(entries[i]);
		}

		JScrollPane scroller = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUnitIncrement(5);
		dialog.add(scroller);

		dialog.setVisible(true);

		JScrollBar bar = scroller.getVerticalScrollBar();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				bar.setValue(bar.getMaximum());
			}
		});
	}

	public static void sort(JournalEntry[] entries) {
		boolean changes = true;

		while (changes) {
			changes = false;

			for (int i = 0; i < entries.length - 1; i++) {
				if (entries[i].comesAfter(entries[i + 1])) {
					JournalEntry ent = entries[i];
					entries[i] = entries[i + 1];
					entries[i + 1] = ent;

					changes = true;
				}
			}
		}
	}
}
