/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	GUI component that displays a header with the word 'Goal'.
*/


package tempo.goal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;


public class GoalHeader extends JComponent {
	private static final String text = "Goals";
	private static final Color background = new Color(2, 167, 176);
	private static final Color foreground = new Color(186, 200, 203);

	public GoalHeader() {
		setPreferredSize(new Dimension(200, 20));
	}

	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		gg.setColor(background);
		gg.fillRoundRect(	100,
					0,
					getWidth() - 100,
					getHeight(),
					10,
					10
		);

		FontMetrics met = gg.getFontMetrics();

		gg.setColor(foreground);
		gg.drawString(	"Goals",
				(int)Math.round(getWidth() / 2.0 -
				met.stringWidth("Goals") / 2.0),
				(int)Math.round(getHeight() / 2.0 +
				(met.getAscent() - met.getDescent()) / 2.0)
		);
	}
}
