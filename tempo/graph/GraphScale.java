/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-14
*
*	Draws the scales for a graph.
*/


package tempo.graph;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import tempo.goal.GoalConstants;

import tempo.graph.Graph;


public class GraphScale extends JComponent implements ComponentListener {
	private GraphData data;

	private boolean area;

	private Graph graph;

	public GraphScale(Graph graph, boolean area) {
		this.graph = graph;
		this.area = area;

		setLayout(null);

		data = graph.getData();

		add(graph);
		addComponentListener(this);
		setPreferredSize(new Dimension(1, 150));
	}

	public void paintComponent(Graphics g) {
		if (getHeight() == 0 || getWidth() == 0) {
			return;
		}

		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		gg.setColor(GraphConstants.FOREGROUND);
		gg.setStroke(new BasicStroke(0.2f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));

		int horizontalOffset = 75;
		int verticalOffset = 25;
		int verticalBase = getHeight() - 50;

		int graphHeight = getHeight() - 75;
		int graphWidth = getWidth() - 100;

		int maxColumn = data.size() - 1;
		if (maxColumn < 1) {
			notEnoughData(gg);
			return;
		}

		double maxRow = (area) ? data.getSummatedMaxY() :data.getMaxY();

		double columnInterval = 0.0;
		double rowInterval = 0.0;

		double columnWidth;
		double rowHeight;

		do {
			columnInterval++;
			columnWidth = (columnInterval * graphWidth) / maxColumn;
		} while (columnWidth < 20);

		do {
			rowInterval++;
			rowHeight = (rowInterval * graphHeight) / maxRow;
		} while (rowHeight < 20);

		int displayValue = 0;
		String display;
		int displayWidth;
		int displayX;
		FontMetrics fm = gg.getFontMetrics();
		int centerline = verticalBase + 10;
		int baseline = centerline +
			((fm.getAscent() - fm.getDescent()) / 2);

		for (	double i = horizontalOffset;
			displayValue <= maxColumn;
			i += columnWidth)
		{
			display = Integer.toString(displayValue);
			displayWidth = fm.stringWidth(display);
			displayX = (int)(i - (displayWidth / 2));

			gg.drawString(display, displayX, baseline);

			displayValue += columnInterval;

			gg.drawLine(rnd(i), verticalOffset,rnd(i),verticalBase);
		}

		displayValue = 0;

		for (	double i = verticalBase;
			displayValue <= maxRow;
			i -= rowHeight)
		{

			display = Integer.toString(displayValue);
			displayWidth = fm.stringWidth(display);

			displayX = horizontalOffset - displayWidth - 5;
			baseline = (int)(i+(fm.getAscent() -fm.getDescent())/2);

			gg.drawString(display, displayX, baseline);

			displayValue += rowInterval;

			gg.drawLine(horizontalOffset, rnd(i),
				horizontalOffset + graphWidth, rnd(i));
		}

		gg.drawString("Days",
			(int)Math.round(horizontalOffset + (graphWidth / 2.0)),
			(int)Math.round(verticalBase + 35)
		);
	}

	private void notEnoughData(Graphics2D gg) {
		gg.setColor(new Color(154, 165, 168));
		gg.setFont(gg.getFont().deriveFont((float)60.0));

		String string = GoalConstants.NO_DATA;
		FontMetrics fm = gg.getFontMetrics();
		gg.drawString(string,
			(getWidth() / 2) - (fm.stringWidth(string) / 2),
			 getHeight() - ((fm.getAscent() - fm.getDescent()) /2));
	}

	public void componentResized(ComponentEvent ce) {
		graph.setBounds(75, 25, getWidth() - 100, getHeight() - 75);
	}

	private int rnd(double d) {
		return (int)Math.round(d);
	}

	public void componentShown(ComponentEvent ce) {}
	public void componentHidden(ComponentEvent ce) {}
	public void componentMoved(ComponentEvent ce) {}
}
