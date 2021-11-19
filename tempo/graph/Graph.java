/*
*	Author: Rhys B
*	Created: 2021-11-06
*	Modified: 2021-11-06
*
*	The abstract base class for all graphs (not the GraphScale class).
*/


package tempo.graph;

import javax.swing.JComponent;

import tempo.graph.GraphData;


abstract class Graph extends JComponent {
	protected GraphData data;

	public GraphData getData() {
		return data;
	}
}
