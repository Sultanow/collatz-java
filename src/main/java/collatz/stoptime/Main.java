package collatz.stoptime;

public class Main {

	public static void collatz(int n, int next, int i) {
		System.out.print(next + " ");

		if (next == 1) {
			if (true || (n + 1) % 4 == 0) System.out.print(" #" + i);
			return;
		} else if (next % 2 == 0) {
			collatz(n, next / 2, ++i);
		} else {
			collatz(n, (3 * next + 1) / 2, ++i);
		}
	}
	
	
	public static void collatzStoppingTime(int n, int next, int i) {
		
		if (next < n) {
			if (true || (n + 1) % 4 == 0) System.out.print(" #" + (i - 1));
			return;
		} else if (next % 2 == 0) {
			collatzStoppingTime(n, next / 2, ++i);
		} else {
			collatzStoppingTime(n, (3 * next + 1) / 2, ++i);
		}
	}
	
	
	public static void main(String[] args) {
		
		for (int i=0; i<100; i++) {
			if ((256*i-4) % 9 == 0) {
				System.out.println(i);
			}
		}
		
		/*
		int k = 2;
		
		for (int i=k; i < 100; i++) {
			if (true || (i + 1) % 4 == 0) System.out.print(i);
			
			collatz(i, i, 1);
			
			if (true || (i + 1) % 4 == 0) System.out.println();
		}
		*/
	}

}
