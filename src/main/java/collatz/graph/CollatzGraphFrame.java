package collatz.graph;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import com.mxgraph.swing.mxGraphComponent;

public class CollatzGraphFrame extends JFrame {

	private static final long serialVersionUID = -7690733858841828721L;

	public CollatzGraphFrame(int w, int h) {

		super("Collatz Tree");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CollatzGraph graph = new CollatzGraph(w, h);
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setBackground(Color.WHITE);
		graphComponent.setBorder(new EmptyBorder(10, 10, 10, 10));
		graphComponent.setBackground(Color.WHITE);
		getContentPane().add(graphComponent);
		pack();
	}
}
