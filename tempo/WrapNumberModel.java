/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	Tells the spinners that need to wrap around (i.e. minutes and seconds
*	spinners that have to go from 59 to 0) how to behave.
*/


package tempo;

import javax.swing.SpinnerNumberModel;


public class WrapNumberModel extends SpinnerNumberModel {
	private int min, max;

	public WrapNumberModel(int val, int min, int max) {
		super(val, min, max, 1);

		this.min = min;
		this.max = max;
	}

	public Object getNextValue() {
		int val = (int)getValue();

		if (val == max) {
			return min;
		}

		return val + 1;
	}

	public Object getPreviousValue() {
		int val = (int)getValue();

		if (val == min) {
			return max;
		}

		return val - 1;
	}
}
