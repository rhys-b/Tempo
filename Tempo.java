/*
*	Author: Rhys B
*	Created: 2021-11-05
*	Modified: 2021-11-07
*
*	Main class for the Tempo program.
*/


import com.formdev.flatlaf.IntelliJTheme;

import tempo.goal.Goals;

import tempo.FileManager;
import tempo.Window;


public class Tempo {
	public static void main(String[] args) {
		IntelliJTheme.setup(Tempo.class.getResourceAsStream(
							"carbon.theme.json"));

		FileManager.init();
		Goals.init();

		Window window = new Window();
	}
}
