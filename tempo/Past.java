/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-12
*
*	Class for the 'past' panel of the tempo project.
*/


package tempo;

import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import tempo.ColorLabel;
import tempo.FileManager;
import tempo.LeftLabel;
import tempo.Spacer;

import tempo.goal.Goal;
import tempo.goal.Goals;

import tempo.graph.AreaGraph;
import tempo.graph.GraphConstants;
import tempo.graph.GraphData;
import tempo.graph.GraphScale;
import tempo.graph.LineGraph;

import tempo.journal.JournalButton;


public class Past extends JPanel implements ActionListener, ChangeListener {
	private GraphData questionData;
	private JPanel questionPanel;
	private LineGraph question;

	private JRadioButton week, month, year, forever;

	private static ArrayList<GraphData> dataList =
						new ArrayList<GraphData>();
	private static JPanel goalPanel;

	private ActionListener timegroupListener = new ActionListener() {
		public void actionPerformed(ActionEvent ae) {
			int scale;

			if (week.isSelected()) {
				scale = GraphData.SCALE_WEEK;
			} else if (month.isSelected()) {
				scale = GraphData.SCALE_MONTH;
			} else if (year.isSelected()) {
				scale = GraphData.SCALE_YEAR;
			} else {
				scale = GraphData.SCALE_FOREVER;
			}

			for (int i = 0; i < dataList.size(); i++) {
				dataList.get(i).setScale(scale);
			}

			Past.this.repaint();
		}
	};

	public Past() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JournalButton());

		week = new JRadioButton("Week");
		month = new JRadioButton("Month");
		year = new JRadioButton("Year");
		forever = new JRadioButton("Forever");

		week.addActionListener(timegroupListener);
		month.addActionListener(timegroupListener);
		year.addActionListener(timegroupListener);
		forever.addActionListener(timegroupListener);

		ButtonGroup timegroup = new ButtonGroup();
		timegroup.add(week);
		timegroup.add(month);
		timegroup.add(year);
		timegroup.add(forever);

		JPanel timepanel = new JPanel(new GridLayout(1, 5));
		timepanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		timepanel.add(new JPanel()); // Buffer
		timepanel.add(week);
		timepanel.add(month);
		timepanel.add(year);
		timepanel.add(forever);

		add(timepanel);

		ColorLabel group1 =
			new ColorLabel("Work/School", GraphConstants.COLORS[0]);
		ColorLabel group2 =
			new ColorLabel("Leisure", GraphConstants.COLORS[1]);
		ColorLabel group3 = new ColorLabel("Self-improvement",
						GraphConstants.COLORS[2]);

		JPanel legend = new JPanel(new GridLayout(1, 6));
		legend.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		legend.add(new JPanel()); // buffer
		legend.add(group1);
		legend.add(group2);
		legend.add(group3);
		legend.add(new JPanel()); // buffer

		GraphData dailyData = FileManager.read("daily.csv");
		questionData = FileManager.read("questions.csv");

		dataList.add(dailyData);
		dataList.add(questionData);

		AreaGraph daily = new AreaGraph(dailyData);
		question = new LineGraph(questionData);

		GraphScale dailyScale = new GraphScale(daily, true);
		GraphScale questionScale = new GraphScale(question, false);

		add(new Spacer(50));
		add(new LeftLabel("How do you spend your time?"));
		add(new Spacer(30));
		add(legend);
		add(dailyScale);
		add(new LeftLabel("How are you feeling each day?"));
		add(questionScale);

		forever.setSelected(true);

		JCheckBox question1 = new JCheckBox("Happiness", true);
		JCheckBox question2 = new JCheckBox("Sleep Quality", true);
		JCheckBox question3 = new JCheckBox("Productivity", true);
		JCheckBox question4 = new JCheckBox("Stress", true);
		JCheckBox question5 = new JCheckBox("Social Interaction", true);

		questionPanel = new JPanel(new GridLayout(1, 5));
		questionPanel.setMaximumSize(
			new Dimension(Integer.MAX_VALUE, 30));
		questionPanel.add(new JPanel()); // Buffer
		questionPanel.add(question1);
		questionPanel.add(question2);
		questionPanel.add(question3);
		questionPanel.add(question4);
		questionPanel.add(question5);

		question1.addActionListener(this);
		question2.addActionListener(this);
		question3.addActionListener(this);
		question4.addActionListener(this);
		question5.addActionListener(this);

		add(questionPanel);
		add(new LeftLabel("Goals"));

		goalPanel = new JPanel();
		goalPanel.setLayout(new BoxLayout(goalPanel, BoxLayout.Y_AXIS));
		revalidateGoalPanel();
		add(goalPanel);
	}

	public static void revalidateGoalPanel() {
		goalPanel.removeAll();

		for (int i = 0; i < Goals.size(); i++) {
			Goal g = Goals.get(i);
			goalPanel.add(new LeftLabel(
						g.getName(), false, 16, 50));

			GraphData data = g.getGraphData();
			dataList.add(data);
			if (g.getType() == Goals.TYPE_REPETATIVE) {
				data.setShowExtra(	GraphData.EXTRA_AVERAGE,
							g.getAmount(),
							null,
							null
				);
			} else if (g.getType() == Goals.TYPE_CUMULATIVE) {
				data.setShowExtra(	GraphData.EXTRA_TREND,
							g.getAmount(),
							g.getStart(),
							(Date)g.getTypeData()
				);
			}

			LineGraph graph = new LineGraph(data);
			GraphScale scale = new GraphScale(graph, false);

			goalPanel.add(scale);
		}

		goalPanel.revalidate();
		goalPanel.repaint();
	}

	public void actionPerformed(ActionEvent ae) {
		for (int i = 1; i < questionPanel.getComponentCount(); i++) {
			questionData.setVisible(i - 1, ((JCheckBox)questionPanel
				.getComponent(i)).isSelected());
		}

		question.repaint();
	}

	public void stateChanged(ChangeEvent ce) {
		System.out.println("here");
	}

	public void setQuestionValues(double[] values) {
		questionData.setLast(values);
	}
}
