/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-06
*
*	GUI component that is the column header on all tabes.
*/


package tempo.table;

import javax.swing.GroupLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tempo.table.HeaderLabelData;


public class TableHeader extends JPanel {
	public TableHeader(HeaderLabelData[] data) {
		JPanel buffer = new JPanel();

		GroupLayout layout = new GroupLayout(this);
		GroupLayout.SequentialGroup horizontal =
			layout.createSequentialGroup();
		GroupLayout.ParallelGroup vertical =
			layout.createParallelGroup();

		for (int i = 0; i < data.length; i++) {
			JLabel label = new JLabel(data[i].getTitle());
			label.setHorizontalAlignment(SwingConstants.CENTER);

			horizontal.addComponent(
				label,
				data[i].getMin(),
				data[i].getPref(),
				data[i].getMax()
			);

			vertical.addComponent(label);
		}

		horizontal.addComponent(buffer, TableRow.HEIGHT,
			TableRow.HEIGHT, TableRow.HEIGHT);
		vertical.addComponent(buffer);

		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);

		setLayout(layout);
	}
}
