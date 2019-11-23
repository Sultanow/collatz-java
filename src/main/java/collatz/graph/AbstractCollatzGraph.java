package collatz.graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;

import javax.imageio.ImageIO;

import org.apache.commons.math3.primes.Primes;

import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.view.mxGraph;

public abstract class AbstractCollatzGraph extends mxGraph {

	protected static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);
	protected static final BigInteger TWO = BigInteger.valueOf(2);
	protected static final BigInteger THREE = BigInteger.valueOf(3);
	protected static final BigInteger FOUR = BigInteger.valueOf(4);
	private static final BigInteger EIGHT = BigInteger.valueOf(8);

	protected final int w;
	protected final int h;
	protected final Node[][] grid;

	private final int nodeWidth;
	private final int nodeHeight;
	private final int hSpacing;
	private final int vSpacing;

	public AbstractCollatzGraph(int w, int h) {
		this(w, h, 30, 30, 60, 60);
	}

	public AbstractCollatzGraph(int w, int h, int nodeWidth, int nodeHeight, int hSpacing, int vSpacing) {
		this.w = w;
		this.h = h;
		grid = new Node[h][w];

		this.nodeWidth = nodeWidth;
		this.nodeHeight = nodeHeight;
		this.hSpacing = hSpacing;
		this.vSpacing = vSpacing;
	}

	protected abstract void init();

	public void draw(Node node, Object parent) {
		if (node.vertex == null) {
			node.vertex = insertVertex(parent, String.valueOf(node.value), node.value, node.col * hSpacing,
					node.row * vSpacing, nodeWidth, nodeHeight);

			// if (node.row < h && node.col < w) {
			// grid[node.row][node.col] = node;
			// }

			// if (IntStream.of(new int[]{5,13,17,45}).anyMatch(x -> x ==
			// node.value.intValue())) {
			// setCellStyles(mxConstants.STYLE_FILLCOLOR, "#ffbe27", new Object[] {
			// node.vertex });
			// setCellStyles(mxConstants.STYLE_STROKECOLOR, "#ff7927", new Object[] {
			// node.vertex });
			// }
			if (Primes.isPrime(node.value.intValue())) {
				// setCellStyles(mxConstants.STYLE_FILLCOLOR, "#cc5577", new Object[] {
				// node.vertex });
			}
			if (node.value.mod(THREE).equals(BigInteger.ZERO)) {
				// setCellStyles(mxConstants.STYLE_FILLCOLOR, "#00ff00", new Object[] {
				// node.vertex });
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

	/*
	 * Save CSV: row | col | value | residue_8
	 */
	public void write(Node node, Writer writer) throws IOException {
		String line = node.row + "\t" + node.col + "\t" + node.value + "\t" + node.value.mod(EIGHT);
		writer.write(line);
		writer.write(System.getProperty("line.separator"));

		for (Node each : node.successors) {
			write(each, writer);
			writer.flush();
		}
	}

	public void saveToPNG() {
		try {
			BufferedImage image = mxCellRenderer.createBufferedImage(this, null, 1, Color.WHITE, true, null);
			ImageIO.write(image, "PNG", new File("C:\\tmp\\graph.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveToCSV() {
		// Save CSV: row | col | value | residue_8
		try (Writer writer = new BufferedWriter(new FileWriter("c:/tmp/collatztree.cvs"))) {
			write(Node.root, writer);
		} catch (IOException e) {
			System.out.println("Problem occurs when creating csv file");
			e.printStackTrace();
		}
	}
}
