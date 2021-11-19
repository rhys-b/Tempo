/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-07
*
*	GUI component containing information for a single task.
*	This class is meant to be placed inside of a TableRow.
*/


package tempo;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tempo.table.TableRow;


public class TaskDisplay extends JPanel {
	private static ArrayList<TaskDisplay> list=new ArrayList<TaskDisplay>();

	private JTextField title;
	private TimeDisplay start, end;
	private JComboBox<String> group;

	public TaskDisplay() {
		this("", new Time(), new Time(), 0);
	}

	public TaskDisplay(String inTitle, Time inStart,Time inEnd,int inGroup){
		title = new JTextField(inTitle);
		start = new TimeDisplay(inStart);
		end = new TimeDisplay(inEnd);

		JLabel to = new JLabel(" to ");
		to.setVerticalAlignment(SwingConstants.CENTER);

		String[] groups = {
			"Work/School",
			"Leisure",
			"Self-improvement"
		};

		group = new JComboBox<String>(groups);
		group.setSelectedIndex(inGroup);

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(title,100,GroupLayout.PREFERRED_SIZE,5000)
			.addComponent(start, 0, 100, 210)
			.addComponent(to)
			.addComponent(end, 0, 100, 210)
			.addComponent(group, 0, 100, 200)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(title)
			.addComponent(start)
			.addComponent(to, TableRow.HEIGHT,
				TableRow.HEIGHT, TableRow.HEIGHT)
			.addComponent(end)
			.addComponent(group)
		);

		setLayout(layout);

		list.add(this);
	}

	public String getTitle() {
		return title.getText();
	}

	public int getStartHour() {
		return start.getHour();
	}

	public int getEndHour() {
		return end.getHour();
	}

	public int getStartMinute() {
		return start.getMinute();
	}

	public int getEndMinute() {
		return end.getMinute();
	}

	public String getStartAm() {
		return start.getAm();
	}

	public String getEndAm() {
		return end.getAm();
	}

	public int getGroup() {
		return group.getSelectedIndex();
	}

	public static ArrayList<TaskDisplay> getList() {
		return list;
	}
}
