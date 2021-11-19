/*
*	Author: Rhys B
*	Created: 2021-11-11
*	Modified: 2021-11-13
*
*	Class for the 'future' panel of the tempo project.
*/


package tempo;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import tempo.GoalListPanel;

import tempo.calendar.CalendarView;

import tempo.goal.Goals;

import tempo.table.HeaderLabelData;
import tempo.table.Table;
import tempo.table.TableHeader;
import tempo.table.TableRow;


public class Future extends JPanel implements ActionListener {
	private static Table table;
	private static CalendarView calendar;

	public Future() {
		HeaderLabelData[] headerData = {
			new HeaderLabelData("Title", 150, 500, 5000),
			new HeaderLabelData("Status", 190, 190, 190),
			new HeaderLabelData("Ends", 180, 180, 180),
			new HeaderLabelData("Edit", 75, 75, 75)
		};

		TableHeader header = new TableHeader(headerData);
		table = new Table(header, GoalListPanel.class, false);

		calendar = new CalendarView(Goals.getList());

		revalidateGoalPanel();

		JTabbedPane tabs = new JTabbedPane();

		tabs.add("List", table);
		tabs.add("Calendar", calendar);

		JButton addGoal = new JButton("Add Goal");
		addGoal.addActionListener(this);

		setLayout(new BorderLayout());
		add(tabs);
		add(addGoal, BorderLayout.SOUTH);
	}

	public static void revalidateGoalPanel() {
		table.clear();

		for (int i = 0; i < Goals.size(); i++) {
			table.appendRow(new TableRow(
					new GoalListPanel(Goals.get(i)), true));
		}

		calendar.setList(Goals.getList());
	}

	public void actionPerformed(ActionEvent ae) {
		Goals.showGoalEditDialog(null);
	}
}
