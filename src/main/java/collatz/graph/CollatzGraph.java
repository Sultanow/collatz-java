package collatz.graph;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CollatzGraph extends AbstractCollatzGraph {

	public CollatzGraph(int w, int h) {
		super(w, h);
	}

	// http://www-personal.k-state.edu/~kconrow/allgifs.html
	protected void init() {
		// root
		Node.root = new Node(BigInteger.ONE, null, null);
		grid[0][0] = Node.root;

		int row = 0;
		int col = 0;
		generateSuccessors(Node.root, row, col);
		Node.recalculateColumns();

		List<Node> evenNodes = new ArrayList<Node>();
		collectEvenNodes(Node.root, evenNodes);
		for (Node evenNode : evenNodes) {
			evenNode.absorb();
		}
		Node.recalculateColumns();

		// Begin drawing
		Object parent = getDefaultParent();
		getModel().beginUpdate();
		draw(Node.root, parent);
		// mxGraphLayout layout = new mxParallelEdgeLayout(this);
		// layout.execute(parent);
		getModel().endUpdate();
	}

	private void generateSuccessors(Node node, int row, int col) {
		if (row <= this.h) {
			Node successor1 = new Node(node.value.multiply(TWO), node, Node.root);
			if (node.value.compareTo(FOUR) == 1 && node.value.mod(TWO).equals(BigInteger.ZERO)
					&& (node.value.add(MINUS_ONE)).mod(THREE).equals(BigInteger.ZERO)) {
				if (col <= this.w) {
					Node successor2 = new Node((node.value.add(MINUS_ONE).divide(THREE)), node, Node.root);
					generateSuccessors(successor2, row + 1, col + 1);
				}
			}
			generateSuccessors(successor1, row + 1, col);
		}
	}

	private void collectEvenNodes(Node node, List<Node> evenNodes) {
		if (node.value.mod(TWO).equals(BigInteger.ZERO)) {
			evenNodes.add(node);
		}
		for (Node successor : node.successors) {
			collectEvenNodes(successor, evenNodes);
		}
	}
}
