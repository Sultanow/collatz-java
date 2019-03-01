package collatz.iteration;

import java.math.BigInteger;
import java.util.ArrayList;

import collatz.tree.Word;

public class Main {

	public static void main(String args[]) {
		
		//int[] values = {9, 27, 81, 243, 729, 2187};
		
		int[] values = {3,9,15,21,27,33,39,45,51,57,63,69,99};
		
//		for (int val : values) {
//			Word word = getWord(val);
//			System.out.println(word.getVector() + " = " + word.val + " " + word.s);
//		}
		
		for (int i = 3; i < 999; i+=6) {
			int val = i;
			Word word = getWord(val);
			System.out.println(word.getVector() + " = " + word.val + " " + word.s);
		}
	}

	static Word getWord(int val) {
		int k = 0;
		BigInteger n = BigInteger.valueOf(val);
		StringBuilder s = new StringBuilder();
		
		ArrayList<BigInteger> trajectory = new ArrayList<>();
		trajectory.add(n);
		
		while (n.compareTo(BigInteger.ONE) > 0) {
			if (n.testBit(0)) {
				n = n.multiply(BigInteger.valueOf(3));
				n = n.add(BigInteger.ONE);
				s.append('r');
				trajectory.add(n);
				k++;
			} else {
				n = n.divide(BigInteger.valueOf(2));
				s.append('q');
				trajectory.add(n);
				k++;
			}
		}
		//System.out.print("It took " + k + " iterations!");
		
		return new Word(s.toString(), trajectory.get(0).intValue());
	}
}
