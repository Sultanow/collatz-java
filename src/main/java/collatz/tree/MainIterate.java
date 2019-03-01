package collatz.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import collatz.tree.function.Function;

public class MainIterate {

	private static final int STOP = 3000;

	public static void main(String[] args) {
		ArrayList<Word> words = new ArrayList<Word>();

		int len = 4;
		int k = 0;
		while (k < STOP) {
			int numRows = (int) Math.pow(2, len);
			boolean skip = false;
			for (int i = 0; i < numRows; i++) {

				// Important: words are written reverse!
				boolean[] word = new boolean[len];

				Function f = null;
				for (int j = 0; j < len; j++) {
					int val = numRows * j + i;
					int ret = 1 & (val >>> j);

					word[j] = ret != 0;
					Function left = word[j] ? Function.r : Function.q;

					if (j < 4 && word[j]) {
						skip = true;
						break;
					}

					// rr sequences are not allowed
					if (j > 0 && word[j - 1] && word[j]) {
						skip = true;
						break;
					}

					if (j > 0 && word[j] && f.eval(1) % 3 != 1) {
						skip = true;
						break;
					}

					if (j == 0) {
						f = left;
					} else {
						f = left.compose(f);
					}
				}

				if (!skip) {
					words.add(new Word(f.name, f.eval(1)));
					k++;
				}
				skip = false;
			}

			len++;
		}

		Collections.sort(words, Utils.comparatorVec);
		for (Word w : words) {
			List<Integer> primeFactors = Utils.primeFactors(w.val);
			if (w.val % 2 == 1) {
				System.out.println(w.getVector() + " = " + w.val + " " + w.s + " " + primeFactors);
			}
		}
	}
}
