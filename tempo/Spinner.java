/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-11
*
*	An extension of JSpinner that allows the scroll wheel to change the
*	value in the spinner.
*/


package tempo;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class Spinner extends JSpinner implements MouseWheelListener {
	private SpinnerNumberModel snm;

	public Spinner(SpinnerNumberModel snm) {
		super(snm);
		this.snm = snm;
		addMouseWheelListener(this);
	}

	public void mouseWheelMoved(MouseWheelEvent me) {
		if (me.getWheelRotation() > 0) {
			Object o = snm.getPreviousValue();
			if (o != null) {
				snm.setValue(o);
			}
		} else {

			Object o = snm.getNextValue();
			if (o != null) {
				snm.setValue(o);
			}
		}
	}

	public double getDouble() {
		return (double)snm.getValue();
	}

	public int getInt() {
		return (int)snm.getValue();
	}
}
