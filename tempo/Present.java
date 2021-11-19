/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-12
*
*	Class for the 'present' panel of the tempo project.
*/


package tempo;

import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tempo.goal.DailyGoalPanel;
import tempo.goal.GoalHeader;
import tempo.goal.Goals;

import tempo.QuestionSlider;

import tempo.table.HeaderLabelData;
import tempo.table.Table;
import tempo.table.TableHeader;

import tempo.textinput.TextInput;


public class Present extends JPanel {
	private static ArrayList<QuestionSlider> sliders =
		new ArrayList<QuestionSlider>();
	private static TextInput bestPartOfDay;
	private static QuestionSlider question1, question2,
		question3, question4, question5;
	private static JPanel goalPanel;

	public Present() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new LeftLabel("What did you do today?"));

		Table dailyTasks = new Table(generateHeader(),
						TaskDisplay.class, true);
		add(dailyTasks);

		ArrayList<TaskDisplay> dailyList = FileManager.loadToday();
		for (int i = 0; i < dailyList.size(); i++) {
			dailyTasks.append(dailyList.get(i));
		}

		int[] questionValues = FileManager.loadQuestions();

		add(new LeftLabel("Indicate today's level of:"));

		question1 = new QuestionSlider("Happiness");
		sliders.add(question1);
		question1.setValue(questionValues[0]);
		add(question1);

		question2 = new QuestionSlider("Sleep Quality");
		sliders.add(question2);
		question2.setValue(questionValues[1]);
		add(question2);

		question3 = new QuestionSlider("Productivity");
		sliders.add(question3);
		question3.setValue(questionValues[2]);
		add(question3);

		question4 = new QuestionSlider("Stress");
		sliders.add(question4);
		question4.setValue(questionValues[3]);
		add(question4);

		question5 = new QuestionSlider("Social Interaction");
		sliders.add(question5);
		question5.setValue(questionValues[4]);
		add(question5);

		add(new LeftLabel("What was the best part of your day?"));

		bestPartOfDay = new TextInput();
		bestPartOfDay.setText(FileManager.loadBestPartOfDay());
		add(bestPartOfDay);

		add(new LeftLabel("Goals To Do Today"));

		goalPanel = new JPanel();
		goalPanel.setLayout(new GridLayout(0, 1));
		revalidateGoalPanel();
		add(goalPanel);

		JPanel bottomBuffer = new JPanel();
		bottomBuffer.setPreferredSize(new Dimension(-1, 20));
		add(bottomBuffer);
	}

	public static void revalidateGoalPanel() {
		goalPanel.removeAll();

		for (int i = 0; i < Goals.size(); i++) {
			if (!Goals.get(i).isDoneToday()) {
				goalPanel.add(new DailyGoalPanel(Goals.get(i)));
			}
		}

		goalPanel.revalidate();
		goalPanel.repaint();
	}

	private TableHeader generateHeader() {
		HeaderLabelData[] data = {
			new HeaderLabelData(
				"Title", 115, GroupLayout.PREFERRED_SIZE, 5000),
			new HeaderLabelData("Start Time", 15, 100, 210),
			new HeaderLabelData("End Time", 15, 100, 210),
			new HeaderLabelData("Category", 15, 100, 200)
		};

		return new TableHeader(data);
	}

	public static ArrayList<QuestionSlider> getSliders() {
		return sliders;
	}

	public static double[] getSliderValues() {
		double[] out = new double[5];

		out[0] = question1.getValue();
		out[1] = question2.getValue();
		out[2] = question3.getValue();
		out[3] = question4.getValue();
		out[4] = question5.getValue();

		return out;
	}

	public static String getBestPartOfDay() {
		return bestPartOfDay.getText();
	}
}
