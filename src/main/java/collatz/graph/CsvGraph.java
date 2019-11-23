package collatz.graph;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;

public class CsvGraph extends AbstractCollatzGraph {
	
	private static final Pattern PATTERN_CSV = Pattern.compile("\\d+,(\\d+),(\\d+),.+");

	private BigInteger[][] successorMap;
	private Map<BigInteger, Node> predecessorMap = new HashMap<>();
	private Map<Node, Node> loopBacks = new HashMap<>();

	private final String file;
	private final int root;
	
	public CsvGraph(int w, int h, int nodeWidth, int nodeHeight, int hSpacing, int vSpacing, String file, int root) {
		super(w, h, nodeWidth, nodeHeight, hSpacing, vSpacing);
		
		this.file = file;
		this.root = root;
	}

	protected void init() {
		Node.root = new Node(BigInteger.valueOf(root), null, null);
		grid[0][0] = Node.root;

		File resourcesPath = new File(CsvGraph.class.getClassLoader().getResource(".").getFile());
		Path path = Paths.get(resourcesPath.getAbsolutePath(), file);
		try {
			List<String> lines = Files.readAllLines(path);
			int lineCount = lines.size() - 1;
			successorMap = new BigInteger[lineCount][2];
			int i = 0;
			for (String line : lines) {
				if (i > 0) {
					Matcher matcher = PATTERN_CSV.matcher(line);
					if (matcher.matches()) {
						successorMap[i - 1][0] = BigInteger.valueOf(Long.valueOf(matcher.group(1)));
						successorMap[i - 1][1] = BigInteger.valueOf(Long.valueOf(matcher.group(2)));
					}
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int row = 0;
		int col = 0;
		generateSuccessors(Node.root, row, col);
		Node.recalculateColumns();

		// Begin drawing
		Object parent = getDefaultParent();
		getModel().beginUpdate();
		draw(Node.root, parent);
		drawLoopBacks(parent);
		getModel().endUpdate();
	}

	public void drawLoopBacks(Object parent) {
		if (!loopBacks.isEmpty()) {
			this.view.setTranslate(new mxPoint(20, 0));

			Hashtable<String, Object> loopBackEdgeStyle = new Hashtable<String, Object>();
			loopBackEdgeStyle.put(mxConstants.STYLE_EXIT_X, -1);
			loopBackEdgeStyle.put(mxConstants.STYLE_EXIT_Y, 0);
			loopBackEdgeStyle.put(mxConstants.STYLE_ENTRY_X, -1);
			loopBackEdgeStyle.put(mxConstants.STYLE_ENTRY_Y, 0);
			loopBackEdgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL);
			stylesheet.putCellStyle("LOOP_BACK", loopBackEdgeStyle);

			for (Map.Entry<Node, Node> loopBack : loopBacks.entrySet()) {
				Object edge = insertEdge(parent, loopBack.getKey().value + "_" + loopBack.getValue().value, null,
						loopBack.getKey().vertex, loopBack.getValue().vertex, "edgeStyle=LOOP_BACK");
				((mxCell)edge).setStyle("LOOP_BACK");
			}
		}
	}

	private void generateSuccessors(Node node, int row, int col) {
		predecessorMap.put(node.value, node);
		if (row <= this.h) {
			int i = 0;
			for (BigInteger[] key : successorMap) {
				if (key[0].equals(node.value)) {
					Node existing = predecessorMap.get(key[1]);
					if (existing != null) {
						loopBacks.put(node, existing);
					} else {
						Node successor = new Node(key[1], node, Node.root);
						generateSuccessors(successor, row + 1, col + i);
					}
				}
			}
		}
	}
}
