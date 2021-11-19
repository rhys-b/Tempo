/*
*	Author: Rhys B
*	Created: 2021-11-13
*	Modified: 2021-11-13
*
*	GUI dialog opened when the user tries to delete a goal.
*/


package tempo.goal;

import java.awt.Container;
import java.awt.Desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import tempo.FileManager;
import tempo.Window;

import tempo.goal.Goal;


public class GoalDeleteDialog extends JDialog
					implements	ComponentListener,
							ActionListener,
							HyperlinkListener
{
	private JEditorPane editor = new JEditorPane();
	private JCheckBox read = new JCheckBox("I have read the resources");
	private JButton delete = new JButton("Delete goal");
	private JButton cancel = new JButton("Cancel");

	public GoalDeleteDialog(Goal goal) {
		super(Window.getFrame(), goal.getName(), true);

		editor.setEditable(false);
		editor.setContentType("text/html");
		editor.addHyperlinkListener(this);

		try {
			editor.read(GoalDeleteDialog.class.getResourceAsStream(
						"delete_motivation.html"),null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setSize(500, 300);
		setLocationRelativeTo(Window.getFrame());
		addComponentListener(this);

		setLayout(null);
		add(read);
		add(cancel);
		add(editor);

		read.addActionListener(this);

		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FileManager.deleteGoal(Goals.indexOf(goal));

				Goals.remove(goal);
				Goals.revalidateEverything();

				GoalDeleteDialog.this.dispose();
			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				GoalDeleteDialog.this.dispose();
			}
		});
	}

	public void componentResized(ComponentEvent ce) {
		Container content = getContentPane();
		int w = content.getWidth(), h = content.getHeight();

		editor.setBounds(5, 5, w - 10, h - 40);
		read.setBounds(5, h - 35, w - 270, 30);
		delete.setBounds(w - 260, h - 35, 150, 30);
		cancel.setBounds(w - 105, h - 35, 100, 30);
	}

	public void actionPerformed(ActionEvent ae) {
		if (read.isSelected()) {
			add(delete);
		} else {
			remove(delete);
		}

		revalidate();
		repaint();
	}

	public void display() {
		setVisible(true);
	}

	public void hyperlinkUpdate(HyperlinkEvent he) {
		try {
			if (he.getEventType() ==
					HyperlinkEvent.EventType.ACTIVATED) {
				Desktop.getDesktop().browse(
							he.getURL().toURI());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void componentHidden(ComponentEvent ce) {}
	public void componentShown(ComponentEvent ce) {}
	public void componentMoved(ComponentEvent ce) {}
}
