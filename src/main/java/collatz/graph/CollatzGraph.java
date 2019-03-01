
package collatz.graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.math3.primes.Primes;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class CollatzGraph extends mxGraph {

	private static final BigInteger THREE = BigInteger.valueOf(3);
	private static final BigInteger FOUR = BigInteger.valueOf(4);
	private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);
	
	private final Node root = new Node(BigInteger.ONE, null, null);
	private final int w;
	private final int h;
	private final Node[][] grid;

	public CollatzGraph(int w, int h) {
		Node.root = root;

		this.w = w;
		this.h = h;
		grid = new Node[h][w];

		generateCollatzTree();
	}

	// http://www-personal.k-state.edu/~kconrow/allgifs.html
	private void generateCollatzTree() {
		// root
		grid[0][0] = root;

		int row = 0;
		int col = 0;
		generateSuccessors(root, row, col);

		Node.recalculateColumns();
		List<Node> evenNodes = new ArrayList<Node>();
		collectEvenNodes(root, evenNodes);
		for (Node evenNode : evenNodes) {
			evenNode.absorb();
		}
		Node.recalculateColumns();

		// Begin drawing
		Object parent = getDefaultParent();
		getModel().beginUpdate();
		draw(root, parent);

		// mxGraphLayout layout = new mxParallelEdgeLayout(this);
		// layout.execute(parent);

		getModel().endUpdate();

		// save to file
		// try {
		// BufferedImage image = mxCellRenderer.createBufferedImage(this, null, 1,
		// Color.WHITE, true, null);
		// ImageIO.write(image, "PNG", new File("C:\\tmp\\graph.png"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void draw(Node node, Object parent) {

		if (node.vertex == null) {
			node.vertex = insertVertex(parent, String.valueOf(node.value), node.value, node.col * 60, node.row * 60, 30,
					30);

			// if (node.row < h && node.col < w) {
			// grid[node.row][node.col] = node;
			// }
			if (Primes.isPrime(node.value.intValue())) {
				setCellStyles(mxConstants.STYLE_FILLCOLOR, "#cc5577", new Object[] { node.vertex });
			}
			if (node.value.mod(THREE).equals(BigInteger.ZERO)) {
				setCellStyles(mxConstants.STYLE_FILLCOLOR, "#00ff00", new Object[] { node.vertex });
			}
		}
		if (node.predecessor != null) {
			/*
			 * since of pre-order traversal this case is never reached
			 *
			 * if (node.predecessor.vertex == null) { node.predecessor.vertex =
			 * insertVertex(parent, String.valueOf(node.predecessor.value),
			 * node.predecessor.value, node.predecessor.col * 60, node.predecessor.row * 60,
			 * 30, 30); }
			 */
			insertEdge(parent, node.predecessor.value + "_" + node.value, null, node.predecessor.vertex, node.vertex,
					"edgeStyle=orthogonalEdgeStyle");
		}

		for (Node each : node.successors) {
			draw(each, parent);
		}
	}

	private void generateSuccessors(Node node, int row, int col) {
		if (row <= this.h) {
			Node successor1 = new Node(node.value.multiply(BigInteger.TWO), node, root);
			if (node.value.compareTo(FOUR) == 1 && node.value.mod(BigInteger.TWO).equals(BigInteger.ZERO)
					&& (node.value.add(MINUS_ONE)).mod(THREE).equals(BigInteger.ZERO)) {
				if (col <= this.w) {
					Node successor2 = new Node((node.value.add(MINUS_ONE).divide(THREE)), node, root);
					generateSuccessors(successor2, row + 1, col + 1);
				}
			}
			generateSuccessors(successor1, row + 1, col);
		}
	}

	private void collectEvenNodes(Node node, List<Node> evenNodes) {
		if (node.value.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
			evenNodes.add(node);
		}
		for (Node successor : node.successors) {
			collectEvenNodes(successor, evenNodes);
		}
	}
}
