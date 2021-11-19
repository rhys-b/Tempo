/*
*	Author: Rhys B
*	Created: 2021-11-11
*	Modified: 2021-11-11
*
*	Contains information on a linear function.
*/


package tempo;


public class Line {
	private double slope, intercept;

	public Line(double slope, double intercept) {
		this.slope = slope;
		this.intercept = intercept;
	}

	public double f(int x) {
		return slope * x + intercept;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

	public void setIntercept(double intercept) {
		this.intercept = intercept;
	}

	public double getSlope() {
		return slope;
	}

	public double getIntercept() {
		return intercept;
	}
}
