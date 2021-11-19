/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	Contains information regarding the min, max, and preferred size
*	of a label, in addition to the label text.
*/


package tempo.table;


public class HeaderLabelData {
	private String title;
	private int pref, min, max;

	public HeaderLabelData(String title, int min, int pref, int max) {
		this.title = title;
		this.min = min;
		this.pref = pref;
		this.max = max;
	}

	public int getPref() {
		return pref;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public String getTitle() {
		return title;
	}
}
