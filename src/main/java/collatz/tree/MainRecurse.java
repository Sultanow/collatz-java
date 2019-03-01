package collatz.tree;

import java.util.Collections;

public class MainRecurse {

	public static void main(String args[]) {

		Tree tree = new Tree();
		tree.traverse(tree.root, 0, 0);

		Collections.sort(tree.words, Utils.comparatorS);
		int i = 0;
		for (Word w : tree.words) {
			if (i > 200) {
				break;
			}
			System.out.println(w.s + " = " + w.val + " " + Utils.primeFactors(w.val));
			i++;
		}
	}
}