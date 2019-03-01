package collatz.tree;

import java.util.ArrayList;
import java.util.List;

public class Word {
	public final String s;
	public final int val;

	public Word(String s, int val) {
		this.s = s;
		this.val = val;
	}

	public List<Integer> getVector() {
		return getVector(s);
	}
	
	public static List<Integer> getVector(String s) {
		ArrayList<Integer> vector = new ArrayList<Integer>();

		int i = 1;
		char[] chars = s.toCharArray();
		int len = chars.length;

		int count = 0;
		while (i < len) {
			char c = chars[i];
			
			if (c != 'r') {
				count++;
			} else {
				vector.add(0, count);
				count = 0;
			}
			i++;
		}
		vector.add(0, count);
		
		return vector;
	}
}
