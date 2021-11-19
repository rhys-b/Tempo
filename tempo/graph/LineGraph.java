/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-12
*
*	A line graph GUI component.
*/


package tempo.graph;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.util.Date;

import tempo.FileManager;

import tempo.graph.Graph;


public class LineGraph extends Graph {
	private boolean showTrendline = false, showAverage = false;
	private boolean preferredAverage = false, preferredTrendline = false;

	public LineGraph(GraphData data) {
		this.data = data;
	}

	public void paintComponent(Graphics g) {
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		int sz = data.size(), hi = getHeight();

		if (sz < 1) {
			return;
		}

		double Xscl = (double)getWidth() / (sz - 1);
		double Yscl = hi / data.getMaxY();

		gg.setStroke(new BasicStroke(
			4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		for (int i = 0; i < data.lines(); i++) {
			gg.setColor(data.colorFor(i));

			for (int j = 0; j < sz - 1; j++) {
				gg.drawLine(rnd(j * Xscl),
					rnd(hi - (data.get(j, i) * Yscl)),
					rnd((j + 1) * Xscl),
					rnd(hi - (data.get(j + 1, i) * Yscl)));
			}
		}
	}

	public int rnd(double d) {
		return (int)Math.round(d);
	}
}
