/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-14
*
*	Main frame and window class for the Tempo program.
*/


package tempo;

import java.awt.Frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.imageio.ImageIO;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import tempo.Past;
import tempo.Present;
import tempo.Future;


public class Window extends JFrame implements WindowListener, ChangeListener {
	private JTabbedPane tabs;
	private Past past;
	private static Frame frame;

	public Window() {
		super("Tempo");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			setIconImage(ImageIO.read(Window.class
				.getResourceAsStream("icon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		tabs = new JTabbedPane();

		past = new Past();
		Present present = new Present();
		Future future = new Future();

		JScrollPane pastScroll = new JScrollPane(past);
		pastScroll.getVerticalScrollBar().setUnitIncrement(5);
		JScrollPane presentScroll = new JScrollPane(present);
		presentScroll.getVerticalScrollBar().setUnitIncrement(5);

		tabs.add("Past", pastScroll);
		tabs.add("Present", presentScroll);
		tabs.add("Future", future);

		tabs.setSelectedIndex(1);
		tabs.addChangeListener(this);

		frame = this;

		addWindowListener(this);
		add(tabs);
		setVisible(true);
	}

	public void windowClosing(WindowEvent we) {
		FileManager.save();
	}

	public void stateChanged(ChangeEvent ce) {
		if (tabs.getSelectedIndex() == 0) {
			double[] questions = Present.getSliderValues();
			past.setQuestionValues(questions);
		}
	}

	public static Frame getFrame() {
		return frame;
	}

	public void windowDeactivated(WindowEvent we) {}
	public void windowActivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
}
