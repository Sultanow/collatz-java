package collatz.test;

public class TripleTest {

	public static void main(String[] args) {
		int n = 3;
		
		System.out.println("x1" + "\t" + "xi" + "\t" + "a");
		for (int x1 = 1; x1 < 100000; x1 += 2) {
			for (int a = 1; a < 100; a++) {
				double xi = Math.pow(2, a) * x1 - 1;
				if (xi % n == 0) {
					xi = xi / n;
					System.out.println(x1 + "\t" + xi + "\t" + a);
				}
			}
		}
	}
}
