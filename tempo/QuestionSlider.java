/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-10
*
*	GUI component for the 'present' panel's sliders.
*/


package tempo;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class QuestionSlider extends JPanel {
	private JSlider slider;

	public QuestionSlider(String question) {
		JLabel title = new JLabel(question);

		JLabel worse = new JLabel("Worse");
		JLabel better = new JLabel("Better");

		slider = new JSlider(0, 100, 50);

		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(10)
			.addComponent(title, 150, 150, 150)
			.addGap(10)
			.addComponent(worse)
			.addComponent(slider)
			.addComponent(better)
			.addGap(10)
		);
		layout.setVerticalGroup(layout.createParallelGroup()
			.addComponent(title, 40, 40, 40)
			.addComponent(worse, 40, 40, 40)
			.addComponent(slider, 40, 40, 40)
			.addComponent(better, 40, 40, 40)
		);

		setLayout(layout);
	}

	public void setValue(int val) {
		slider.setValue(val);
	}

	public int getValue() {
		return (int)slider.getValue();
	}
}
