/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-11
*
*	Main class for the functionality of tables.
*/


package tempo.table;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tempo.table.TableHeader;


@SuppressWarnings({"unchecked", "rawtypes"})
public class Table extends JPanel implements ActionListener {
	private Class sample;
	private JPanel content = new JPanel();

	public Table(TableHeader header, Class sample, boolean showAdd) {
		this.sample = sample;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(-1, 200));

		if (showAdd) {
			JButton add = new JButton("New");
			add(add, BorderLayout.SOUTH);
			add.addActionListener(this);
		}

		add(header, BorderLayout.NORTH);

		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		JScrollPane scroller = new JScrollPane(content);
		scroller.getVerticalScrollBar().setUnitIncrement(5);
		add(scroller);
	}

	public void actionPerformed(ActionEvent ae) {
		// Called when the 'add' button is pressed.

		try {
			append((JComponent)sample
				.getDeclaredConstructor().newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void append(JComponent component) {
		content.add(new TableRow(component));
		content.revalidate();
	}

	public void appendRow(TableRow row) {
		content.add(row);
		content.revalidate();
		content.repaint();
	}

	public void clear() {
		content.removeAll();
		content.revalidate();
		content.repaint();
	}
}
