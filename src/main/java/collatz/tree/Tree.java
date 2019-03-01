package collatz.tree;

import java.util.ArrayList;

import collatz.tree.function.Function;

public class Tree {

	final static int MAX_STEPS = 45;

	final Function root = Function.q;

	final ArrayList<Word> words = new ArrayList<Word>();
	
	void traverse(Function f, int l, int r) {
		if (f == null) {
			return;
		}

		if (f.eval(1) % 2 == 1) {
			words.add(new Word(f.name, f.eval(1)));
		}

		f.populate();
		
		if (l < MAX_STEPS && r < MAX_STEPS) {
			traverse(f.left, l++, r);
			traverse(f.right, l, r++);
		}
	}
}
