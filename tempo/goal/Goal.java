/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-14
*
*	Contains information on a single goal.
*/


package tempo.goal;

import java.util.ArrayList;
import java.util.Date;

import tempo.FileManager;

import tempo.goal.Goals;

import tempo.graph.GraphData;


public class Goal {
	private String name, action, noun;
	private double amount;
	private int type;
	private boolean isDoneToday;
	private Object typeData;
	private GraphData graphData;
	private Date start;

	public Goal() {
		name = null;
		start = null;
		action = null;
		noun = null;
		type = 0;
		amount = 0;
		typeData = null;
		graphData = null;
		isDoneToday = false;
	}
	
	public Goal(	String name,
			Date start,
			String action,
			double amount,
			String noun,
			int type,
			Object typeData,
			GraphData graphData,
			boolean isDoneToday)
	{

		this.name = name;
		this.start = start;
		this.action = action;
		this.noun = noun;
		this.type = type;
		this.amount = amount;
		this.typeData = typeData;
		this.graphData = graphData;
		this.isDoneToday = isDoneToday;
	}

	public String getName() {
		return name;
	}

	public Date getStart() {
		return start;
	}

	public String getAction() {
		return action;
	}

	public double getAmount() {
		return amount;
	}

	public String getNoun() {
		return noun;
	}

	public int getType() {
		return type;
	}

	public Object getTypeData() {
		return typeData;
	}

	public GraphData getGraphData() {
		return graphData;
	}

	public boolean isDoneToday() {
		return isDoneToday;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setNoun(String noun) {
		this.noun = noun;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setTypeData(Object typeData) {
		this.typeData = typeData;
	}

	public void setGraphData(GraphData graphData) {
		this.graphData = graphData;
	}

	public void setDailyValue(double num) {
		double[] dat = new double[1];

		if (type == Goals.TYPE_REPETATIVE || graphData.size() == 0) {
			dat[0] = num;
		} else {
			dat[0] = num + graphData.get(graphData.size() - 1, 0);
		}

		graphData.add(dat);
		isDoneToday = true;
	}

	@Override
	public String toString() {
		String description = action + " " + amount + " " + noun;

		if (type == Goals.TYPE_REPETATIVE) {
			description += " each " + typeData;
		}

		return description;
	}

	public boolean isOnTrack() {
		if (type == Goals.TYPE_CUMULATIVE) {
			int days = FileManager.daysDifference(start,
								(Date)typeData);

			double extrapolation = graphData.getLine().f(days);

			return extrapolation >= amount;
		} else {
			return graphData.getAverage() >= amount;
		}
	}

	public boolean hasData() {
		return graphData.hasData();
	}
}
