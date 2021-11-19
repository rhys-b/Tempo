/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-12
*
*	An area graph GUI component.
*/


package tempo.graph;

import java.awt.geom.Path2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import tempo.graph.Graph;
import tempo.graph.GraphConstants;


public class AreaGraph extends Graph {
	public AreaGraph(GraphData data) {
		this.data = data;
	}

	public void paintComponent(Graphics g) {
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		int sz = data.size(), wid = getWidth(), hi = getHeight();
		double Xscl = (double)wid / (sz - 1);

		if (sz < 1) {
			return;
		}

		double max = data.getSummatedMaxY();
		double Yscl = hi / max;

		double[] bottomRow = new double[sz];
		double[] topRow = new double[sz];

		for (int i = 0; i < bottomRow.length; i++) {
			bottomRow[i] = hi;
		}

		for (int j = 0; j < data.lines(); j++) {
			for (int i = 0; i < sz; i++) {
				topRow[i] = bottomRow[i]-(data.get(i, j) *Yscl);
			}

			Path2D.Double path = new Path2D.Double();
			path.moveTo(0, bottomRow[0]);

			for (int i = 1; i < sz; i++) {
				path.lineTo(i * Xscl, bottomRow[i]);
			}

			path.lineTo((sz - 1) * Xscl, topRow[sz - 1]);

			for (int i = sz - 1; i >= 0; i--) {
				path.lineTo(i * Xscl, topRow[i]);
			}

			path.lineTo(0, bottomRow[0]);

			gg.setColor(data.colorFor(j));
			gg.fill(path);

			for (int i = 0; i < sz; i++) {
				bottomRow[i] = topRow[i];
			}
		}
	}
}
