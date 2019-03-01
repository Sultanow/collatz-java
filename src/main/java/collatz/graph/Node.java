package collatz.graph;

import java.math.BigInteger;
import java.util.ArrayList;

public class Node {
	public static Node root;

	public final BigInteger value;

	public int row;
	public int col;
	public int breadth = 1;

	public final ArrayList<Node> successors = new ArrayList<>();
	public Node predecessor;

	public Object vertex;

	public Node(BigInteger value, Node predecessor, Node root) {
		this.value = value;
		this.predecessor = predecessor;

		if (predecessor == null) {
			col = 0;
			row = 0;
		} else {
			row = predecessor.row + 1;
			// calculateColumn();
			predecessor.addSuccessor(this);
		}
	}

	private void addSuccessor(Node successor) {
		this.successors.add(successor);
		updateBreadth();
		// recalculateColumns(root, successor.col);
	}

	private static void recalculateColumns(Node node, int col) {
		if (node.col >= col) {
			node.calculateColumn();
		}
		for (Node successor : node.successors) {
			recalculateColumns(successor, col);
		}
	}

	public static void recalculateColumns() {
		Node.recalculateColumns(root, 0);
	}

	private void calculateColumn() {
		col = 0;
		if (predecessor != null) {
			col += predecessor.col;
			for (Node previousSibling : predecessor.successors) {
				if (previousSibling != this) {
					col += previousSibling.breadth;
				} else {
					break;
				}
			}
		}
	}

	public void absorb() {
		for (Node successor : successors) {
			successor.predecessor = predecessor;
			moveup(successor);
		}
		predecessor.successors.remove(this);
		predecessor.successors.addAll(successors);
		predecessor.updateBreadth();
	}

	public void moveup(Node node) {
		node.row--;
		for (Node successor : node.successors) {
			moveup(successor);
		}
	}

	public void updateBreadth() {
		if (!successors.isEmpty()) {
			breadth = successors.stream().mapToInt(node -> node.breadth).sum();

			/*
			 * Update predecessor only if we are not a leaf. When a successor is added to a
			 * node, the node's breadth will already be updated.
			 */
			if (predecessor != null) {
				predecessor.updateBreadth();
			}
		}
	}
}
