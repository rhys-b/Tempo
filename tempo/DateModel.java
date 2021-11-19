/*
*	Author: Rhys B
*	Created: 2021-11-12
*	Modified: 2021-11-12
*
*	Only overrides method setCalendarField so that the day value is changed
*	in the implementing spinner.
*/


package tempo;

import javax.swing.SpinnerDateModel;


public class DateModel extends SpinnerDateModel {
	public void setCalendarField(int beans) {
		// do nothing.
	}
}
