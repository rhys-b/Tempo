/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-10
*
*	A GUI component that allows text input and has spell check.
*/


package tempo.textinput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import javax.swing.text.StyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


public class TextInput extends JScrollPane implements KeyListener, Runnable {
	private static final String[] englishWords = loadWords();
	private static final SimpleAttributeSet normal = makeNormal();
	private static final SimpleAttributeSet underline = makeUnderline();
	private static final char[] NON_DELIMITERS = generateNonDelimiters();

	private JTextPane editor = new JTextPane();
	private StyledDocument doc = editor.getStyledDocument();
	private static boolean active = true;

	public TextInput() {
		setViewportView(editor);

		setHorizontalScrollBarPolicy(
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setPreferredSize(new java.awt.Dimension(-1, 100));

		editor.addKeyListener(this);
	}

	public String getText() {
		return editor.getText();
	}

	public void setText(String text) {
		editor.setText(text);
		run();
	}

	public void keyTyped(KeyEvent ke) {
		SwingUtilities.invokeLater(this);
	}

	public void run() {
		// This whole method is for spell check.

		if (!active) {
			return;
		}

		// Reset any highlighting.
		doc.setCharacterAttributes(0, doc.getLength(), normal, true);

		// Get the text in the file and separate them into the words.
		StringLocation[] words = retrieveWords();

		if (words == null) {
			return;
		}

		for (int i = 0; i < words.length; i++) {
			if (binarySearch(
				englishWords, words[i].getString()) < 0) {

				doc.setCharacterAttributes(
					words[i].getLocation(),
					words[i].getLength(),
					underline,
					false
				);
			}
		}
	}

	public boolean arrayContains(char[] arr, char c) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == c) {
				return true;
			}
		}

		return false;
	}

	public int binarySearch(String[] arr, String term) {
		int min = -1, max = arr.length, mid, comp;

		while (min + 1 != max) {
			mid = (min + max) / 2;
			comp = term.compareToIgnoreCase(arr[mid]);

			if (comp == 0) {
				return mid;
			} else if (comp > 0) {
				min = mid;
			} else {
				max = mid;
			}
		}

		return -1;
	}

	public StringLocation[] retrieveWords() {
		// Returns the text in the document as a series of words.

		try {
			String text = doc.getText(0, doc.getLength());
			String currentWord = "";
			ArrayList<StringLocation> list =
				new ArrayList<StringLocation>();
			char c;

			for (int i = 0; i < text.length(); i++) {
				c = text.charAt(i);

				if (arrayContains(NON_DELIMITERS, c)) {
					currentWord += c;
				} else if (!currentWord.equals("")) {
					list.add(new StringLocation(currentWord,
						i - currentWord.length()));
					currentWord = "";
				}
			}

			if (!currentWord.equals("")) {
				list.add(new StringLocation(
					currentWord, text.length() -
					currentWord.length()));
			}

			return list.toArray(new StringLocation[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String[] loadWords() {
		// Sets the array of english words from the data file.

		try {
			Scanner scan = new Scanner(
				TextInput.class.getResourceAsStream(
				"english.txt"));

			ArrayList<String> list = new ArrayList<String>();

			int i = 0;
			while (scan.hasNextLine()) {
				list.add(scan.nextLine().trim());
			}

			scan.close();

			return list.toArray(new String[0]);
		} catch (Exception e) {
			active = false;
			e.printStackTrace();

			return null;
		}
	}

	private static SimpleAttributeSet makeNormal() {
		// Generates and returns the default attribute set.

		return new SimpleAttributeSet();
	}

	private static SimpleAttributeSet makeUnderline() {
		// Generates and returns the attribute set associated with
		// incorrectly typed words (the red underline).

		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setUnderline(set, true);

		return set;
	}

	private static char[] generateNonDelimiters() {
		// Returns an array of characters that ARE NOT delimiters.

		char[] out = new char[54];
		int index = 0;

		// Uppercase letters.
		for (int i = 65; i <= 90; i++) {
			out[index++] = (char)i;
		}

		// Lowercase letters.
		for (int i = 97; i <= 122; i++) {
			out[index++] = (char)i;
		}

		out[index++] = '-';
		out[index++] = '\'';

		return out;
	}

	public void keyPressed(KeyEvent ke) {}
	public void keyReleased(KeyEvent ke) {}
}
