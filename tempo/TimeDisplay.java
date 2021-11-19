/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-07
*
*	GUI component for showing and changing times in a day.
*	This is especially meant to go inside of a TaskDisplay.
*/


package tempo;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import tempo.table.TableRow;

import tempo.Spinner;
import tempo.Time;
import tempo.WrapNumberModel;


public class TimeDisplay extends JPanel {
	private static final int WIDTH = 65;

	private Spinner hour, minute;
	private JComboBox<String> ampm;

	public TimeDisplay() {
		this(new Time());
	}

	public TimeDisplay(Time time) {
		hour = new Spinner(new WrapNumberModel(1, 1, 12));
		minute =new Spinner(new WrapNumberModel(1,0,59));
		((Spinner.NumberEditor)minute.getEditor())
			.getFormat().setMinimumIntegerDigits(2);

		hour.setValue(time.getHour());
		minute.setValue(time.getMinute());

		JLabel colon = new JLabel(":");
		colon.setVerticalAlignment(SwingConstants.CENTER);

		String[] ampmStrings = {"a.m.", "p.m."};
		ampm = new JComboBox<String>(ampmStrings);
		ampm.setSelectedIndex(time.getAmPm());

		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(hour, WIDTH - 5, WIDTH, WIDTH + 5)
			.addComponent(colon)
			.addComponent(minute, WIDTH - 5, WIDTH, WIDTH + 5)
			.addComponent(ampm, 70, 70, 70)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(hour)
			.addComponent(colon, TableRow.HEIGHT,
				TableRow.HEIGHT, TableRow.HEIGHT)
			.addComponent(minute)
			.addComponent(ampm)
		);

		setLayout(layout);
	}

	public int getHour() {
		return hour.getInt();
	}

	public int getMinute() {
		return minute.getInt();
	}

	public String getAm() {
		if (ampm.getSelectedIndex() == 0) {
			return "am";
		} else {
			return "pm";
		}
	}
}
