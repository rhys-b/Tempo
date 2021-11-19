/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-10
*
*	Contains a string with a paired location in a document.
*/


package tempo.textinput;


public class StringLocation {
	private int location;
	private String string;

	public StringLocation(String string, int location) {
		this.location = location;
		this.string = string;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getLocation() {
		return location;
	}

	public String getString() {
		return string;
	}

	public int getLength() {
		return string.length();
	}

	@Override
	public String toString() {
		return "StringLocation from " + location +
			" with value '" + string  + "'";
	}
}
