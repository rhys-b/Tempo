/*
*	Author: Rhys B
*	Created: 2021-11-11
*	Modified: 2021-11-12
*
*	GUI dialog for editing and creating goals.
*/


package tempo.goal;

import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import tempo.DateModel;
import tempo.Future;
import tempo.Past;
import tempo.Present;
import tempo.Spinner;
import tempo.Window;

import tempo.goal.Goal;
import tempo.goal.Goals;

import tempo.graph.GraphData;


public class GoalEditDialog extends JDialog implements	ComponentListener,
							KeyListener,
							ActionListener,
							WindowListener,
							Runnable
{
	public static final int CANCEL = 0, SAVE = 1, DELETE = 2;

	private static final String[] times = {"day", "week", "month", "year"};

	private JTextField title = new JTextField();

	private JLabel actionLbl = new JLabel("Action");
	private JLabel amountLbl = new JLabel("Amount");
	private JLabel nounLbl = new JLabel("Noun");

	private JTextField action = new JTextField();
	private Spinner amount;
	private JTextField noun = new JTextField();
	private JComboBox<String> type;
	private JComboBox<String> time;
	private JSpinner date;

	private JButton delete = new JButton("Delete");
	private JButton cancel = new JButton("Cancel");
	private JButton save = new JButton("Save");

	private int currentState;
	private JComponent[] stateChanger = new JComponent[2];

	private Goal gl;
	private int ret = -1;

	public GoalEditDialog(Goal goal) {
		super(	Window.getFrame(),
			(goal == null) ? "New Goal" : goal.getName(),
			true);

		gl = goal;

		String[] types = {"by", "every"};
		type = new JComboBox<String>(types);
		type.addActionListener(this);
		currentState = type.getSelectedIndex();

		amount = new Spinner(new SpinnerNumberModel(
			0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1.0));

		time = new JComboBox<String>(times);

		DateModel dateModel = new DateModel();

		date = new JSpinner(dateModel);
		((JSpinner.DateEditor)date.getEditor()).getFormat()
						.applyPattern("MMM dd, yyyy");

		actionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		amountLbl.setHorizontalAlignment(SwingConstants.CENTER);
		nounLbl.setHorizontalAlignment(SwingConstants.CENTER);

		title.setText((goal == null) ? "" : goal.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.addKeyListener(this);

		stateChanger[0] = date;
		stateChanger[1] = time;

		if (goal == null) {
			date.setValue(new Date());
		} else {
			if (goal.getType() == Goals.TYPE_REPETATIVE) {
				date.setValue(new Date());
				time.setSelectedItem(goal.getTypeData());
			} else {
				date.setValue(goal.getTypeData());
			}

			title.setText(goal.getName());
			action.setText(goal.getAction());
			amount.setValue(goal.getAmount());
			noun.setText(goal.getNoun());
			type.setSelectedIndex(goal.getType());
		}

		setSize(650, 170);
		setLocationRelativeTo(Window.getFrame());

		addComponentListener(this);
		addWindowListener(this);
		setLayout(null);

		add(title);
		add(actionLbl);
		add(amountLbl);
		add(nounLbl);
		add(action);
		add(amount);
		add(noun);
		add(type);
		add(date);
		add(cancel);
		add(save);

		if (goal != null) {
			add(delete);
		}

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ret = CANCEL;
				GoalEditDialog.this.dispose();
			}
		});

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Goals.showDeleteDialog(goal);

				ret = DELETE;
				GoalEditDialog.this.dispose();
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean startedAsNull = false;

				if (goal == null) {
					gl = new Goal();
					gl.setStart(new Date());

					double[][] y = new double[0][0];
					gl.setGraphData(new GraphData(y));

					startedAsNull = true;
				}

				gl.setName(title.getText());
				gl.setAction(action.getText());
				gl.setAmount((double)amount.getValue());
				gl.setNoun(noun.getText());
				gl.setType(type.getSelectedIndex());

				if (type.getSelectedIndex() ==
							Goals.TYPE_REPETATIVE)
				{
					gl.setTypeData(
							time.getSelectedItem());
				} else {
					gl.setTypeData(date.getValue());
				}

				if (startedAsNull) {
					Goals.add(gl);
				}

				ret = SAVE;
				GoalEditDialog.this.dispose();

				Goals.revalidateEverything();
			}
		});
	}

	public void componentResized(ComponentEvent ce) {
		Container contentPane = getContentPane();
		int w = contentPane.getWidth();
		int h = contentPane.getHeight();

		title.setBounds(5, 5, w - 10, 30);

		int a = w - 480;
		actionLbl.setBounds(5, 40, a, 20);
		amountLbl.setBounds(a + 10, 40, 100, 20);
		nounLbl.setBounds(a + 90, 40, 100, 20);

		action.setBounds(5, 62, a, 30);
		amount.setBounds(a + 10, 62, 100, 30);
		noun.setBounds(a + 115, 62, 100, 30);
		type.setBounds(a + 220, 62, 100, 30);
		time.setBounds(a + 325, 62, 150, 30);
		date.setBounds(a + 325, 62, 150, 30);

		delete.setBounds(5, h - 30, 100, 25);
		cancel.setBounds(w - 210, h - 30, 100, 25);
		save.setBounds(w - 105, h - 30, 100, 25);
	}

	public int display() {
		setVisible(true);

		return ret;
	}

	public void keyTyped(KeyEvent ke) {
		SwingUtilities.invokeLater(this);
	}

	public void run() {
		setTitle(title.getText());
	}

	public void actionPerformed(ActionEvent ae) {
		if (currentState != type.getSelectedIndex()) {
			remove(stateChanger[currentState]);

			currentState = type.getSelectedIndex();
			add(stateChanger[currentState]);

			revalidate();
			repaint();
		}
	}

	public void windowClosing(WindowEvent we) {
		if (ret == -1) {
			ret = CANCEL;
		}
	}

	public void windowDeactivated(WindowEvent we) {}
	public void windowActivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void keyPressed(KeyEvent ke) {}
	public void keyReleased(KeyEvent ke) {}
	public void componentHidden(ComponentEvent ce) {}
	public void componentShown(ComponentEvent ce) {}
	public void componentMoved(ComponentEvent ce) {}
}
