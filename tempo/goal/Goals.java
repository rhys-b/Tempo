/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-13
*
*	Contains the collection of goals, and static methods to help with
*	maintaining them.
*/


package tempo.goal;

import java.util.ArrayList;

import javax.swing.JDialog;

import tempo.FileManager;
import tempo.Future;
import tempo.Past;
import tempo.Present;
import tempo.Window;

import tempo.goal.Goal;
import tempo.goal.GoalDeleteDialog;
import tempo.goal.GoalEditDialog;


public class Goals {
	public static final int TYPE_CUMULATIVE = 0, TYPE_REPETATIVE = 1;

	private static ArrayList<Goal> list;

	public static void init() {
		list = FileManager.readGoals();
	}

	public static int size() {
		return list.size();
	}

	public static Goal get(int i) {
		return list.get(i);
	}

	public static void add(Goal g) {
		list.add(g);
	}

	public static ArrayList<Goal> getList() {
		return list;
	}

	public static void remove(Goal goal) {
		list.remove(goal);
	}

	public static int indexOf(Goal g) {
		return list.indexOf(g);
	}

	public static void revalidateEverything() {
		Present.revalidateGoalPanel();
		Past.revalidateGoalPanel();
		Future.revalidateGoalPanel();
	}

	public static void showGoalEditDialog(Goal goal) {
		GoalEditDialog dialog = new GoalEditDialog(goal);
		dialog.display();
	}

	public static void showDeleteDialog(Goal goal) {
		GoalDeleteDialog dialog = new GoalDeleteDialog(goal);
		dialog.display();
	}

	public static void showGoalInfoDialog(Goal goal) {
		GoalInfoDialog dialog = new GoalInfoDialog(goal);
		dialog.display();
	}
}
