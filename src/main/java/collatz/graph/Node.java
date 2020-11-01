package collatz.graph;

import java.math.BigInteger;
import java.util.ArrayList;

public class Node {
	public static Node root;

	public final BigInteger value;
	public boolean prunable = false;
	
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
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
