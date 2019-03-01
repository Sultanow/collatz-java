package collatz.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Utils {

	public final static Comparator<Word> comparatorS = new Comparator<Word>() {

		@Override
		public int compare(Word w1, Word w2) {
			if (w1.s.length() == w2.s.length()) {
				return w1.s.compareTo(w2.s);
			} else {
				return w1.s.length() - w2.s.length();
			}
		}
	};

	public final static Comparator<Word> comparatorV = new Comparator<Word>() {

		@Override
		public int compare(Word w1, Word w2) {
			return w1.val - w2.val;
		}
	};

	public final static Comparator<Word> comparatorVec = new Comparator<Word>() {

		@Override
		public int compare(Word w1, Word w2) {
			int s1 = w1.getVector().size();
			int s2 = w2.getVector().size();
			return s1 == s2 ? w1.getVector().toString().compareTo(w2.getVector().toString()) : s1 - s2;
		}
	};
	
	public static List<Integer> primeFactors(int numbers) {
		int n = numbers;
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for (int i = 2; i <= n / i; i++) {
			while (n % i == 0) {
				factors.add(i);
				n /= i;
			}
		}
		if (n > 1) {
			factors.add(n);
		}
		return factors;
	}
}
