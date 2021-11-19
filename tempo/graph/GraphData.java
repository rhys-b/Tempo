/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-14
*
*	Contains information neccessary for graphs to be drawn.
*/


package tempo.graph;

import java.awt.Color;

import java.io.File;

import java.util.Date;

import tempo.FileManager;
import tempo.Line;


public class GraphData {
	public static final int SCALE_WEEK = 7,
				SCALE_MONTH = 31,
				SCALE_YEAR = 365,
				SCALE_FOREVER = Integer.MAX_VALUE;

	public static final int	EXTRA_OFF = 0,
				EXTRA_TREND = 1,
				EXTRA_AVERAGE = 2;

	private double[][] y;
	private double[] extra, preferred;
	private boolean[] visible;
	private int scale;

	private int showExtra = EXTRA_OFF;
	private Date prefStart, prefEnd;
	private double prefVal;
	private Line line;

	public GraphData(double[][] y) {
		this.y = y;

		if (y.length > 0) {
			visible = new boolean[y[0].length];

			for (int i = 0; i < visible.length; i++) {
				visible[i] = true;
			}
		} else {
			visible = new boolean[0];
		}

		scale = SCALE_FOREVER;
	}

	public void setShowExtra(int type, double pref, Date start, Date end) {
		showExtra = type;
		prefVal = pref;
		prefStart = start;
		prefEnd = end;

		extra = new double[y.length];
		preferred = new double[y.length];

		if (showExtra == EXTRA_AVERAGE) {
			double total = 0;
			double average = 0;

			if (y.length > 0) {
				for (int i = 0; i < y.length; i++) {
					total += y[i][0];
				}

				average = total / y.length;
			}

			for (int i = 0; i < extra.length; i++) {
				extra[i] = average;
			}

			for (int i = 0; i < preferred.length; i++) {
				preferred[i] = pref;
			}

			line = new Line(0, pref);
		} else if (showExtra == EXTRA_TREND) {
			double xt = 0, yt = 0, xy = 0, x2 = 0, n = y.length;
			double m, b;

			for (int i = 0; i < n; i++) {
				xt += i;
				yt += y[i][0];
				xy += i * y[i][0];
				x2 += i * i;
			}

			m = (n * xy - xt * yt) / (n * x2 - xt * xt);
			b = (yt - m * xt) / n;

			line = new Line(m, b);

			for (int x = 0; x < extra.length; x++) {
				extra[x] = m * x + b;
			}

			int days = FileManager.daysDifference(start, end);

			double increment = pref / days;
			double total = 0;

			for (int i = 0; i < preferred.length; i++) {
				preferred[i] = total;
				total += increment;
			}
		}
	}

	public double get(int x, int line) {
		if (y.length > 0 && showExtra != EXTRA_OFF) {
			if (y[0].length + 1 == line) {
				return preferred[preferred.length - size() + x];
			} else if (y[0].length == line) {
				return extra[extra.length - size() + x];
			}
		}

		return y[y.length - size() + x][getReal(line)];
	}

	public void setLast(double[] val) {
		if (val.length != y[0].length) {
			throw new IllegalArgumentException(
				"Column data size must match current size.");
		}

		for (int i = 0; i < val.length; i++) {
			y[y.length - 1][i] = val[i];
		}

		setShowExtra(showExtra, prefVal, prefStart, prefEnd);
	}

	public void add(double[] vals) {
		double[][] out;

		if (y.length > 0) {
			out = new double[y.length + 1][y[0].length];
		} else {
			out = new double[1][vals.length];

			visible = new boolean[vals.length];
			for (int i = 0; i < vals.length; i++) {
				visible[i] = true;
			}
		}

		for (int i = 0; i < y.length; i++) {
			for (int j = 0; j < out[i].length; j++) {
				out[i][j] = y[i][j];
			}
		}

		for (int i = 0; i < out[0].length; i++) {
			out[out.length - 1][i] = vals[i];
		}

		y = out;

		setShowExtra(showExtra, prefVal, prefStart, prefEnd);
	}

	public double getMaxY() {
		double max = 0;

		for (int i = 0; i < y.length; i++) {
			for (int j = 0; j < y[i].length; j++) {
				if (y[i][j] > max) {
					max = y[i][j];
				}
			}

			if (preferred[i] > max) {
				max = preferred[i];
			}

			if (extra[i] > max) {
				max = extra[i];
			}
		}

		return max;
	}

	public double getSummatedMaxY() {
		double max = 0;

		for (int i = 0; i < y.length; i++) {
			int total = 0;
			for (int j = 0; j < y[i].length; j++) {
				total += y[i][j];
			}

			if (total > max) {
				max = total;
			}
		}

		return max;
	}

	public int size() {
		return Math.min(y.length, scale);
	}

	public int lines() {
		int out = 0;

		for (int i = 0; i < visible.length; i++) {
			if (visible[i]) {
				out++;
			}
		}

		if (showExtra != EXTRA_OFF) {
			return out + 2;
		}

		return out;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public void setVisible(int index, boolean b) {
		visible[index] = b;
	}

	public Color colorFor(int index) {
		if (y.length > 0) {
			if (index == y[0].length) {
				return GraphConstants.AVERAGE;
			} else if (index - 1 == y[0].length) {
				return GraphConstants.PREFERRED;
			}
		}

		return GraphConstants.COLORS[getReal(index)];
	}

	public int getReal(int index) {
		int visibles = 0;

		for (int i = 0; i < visible.length; i++) {
			if (visible[i]) {
				visibles++;
			}

			if (visibles - 1 == index) {
				return i;
			}
		}

		return -1;
	}

	public Line getLine() {
		return line;
	}

	public double getAverage() {
		if (extra.length > 0) {
			return extra[0];
		}

		return 0;
	}

	public boolean hasData() {
		return y.length > 1;
	}
}
