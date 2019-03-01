package collatz.test;

import java.math.BigDecimal;

public class Main {

	private static int[][] p1 = { { 7, 4, 1 }, { 1, 7, 4 } };
	private static int[][] p2 = { { 9, 6, 3 }, { 3, 9, 6 } };

	private static BigDecimal bd_2 = new BigDecimal(2);
	private static BigDecimal bd_3 = new BigDecimal(3);
	private static BigDecimal bd_n1 = new BigDecimal(-1);

	private static int max = 10;

	public static void main(String[] args) {
		check_1();
	}

	private static void check_1() {
		for (int k0 = 0; k0 < max; k0++) {
			int a0 = 2 * (9 * k0 + 1);

			for (int k1 = 0; k1 < max; k1++) {
				int temp = permute(p1, 7 - 3 * (k0 % 3), 0);
				int a1 = 2 * (9 * k1 + temp) + 2;

				for (int k2 = 0; k2 < max; k2++) {
					int a2 = 6 * k2 + 5;

					printResult(a0, a1, a2);
				}
			}
		}
	}

	private static void check_2() {
		for (int k0 = 0; k0 < max; k0++) {
			int a0 = 2 * (9 * k0 + 7 + 1);

			for (int k1 = 0; k1 < max; k1++) {
				int temp = permute(p1, 1 + 3 * (k0 % 3), 0);
				int a1 = 2 * (9 * k1 + temp) + 1;

				for (int k2 = 0; k2 < max; k2++) {
					int a2 = 6 * k2 + 1;

					printResult(a0, a1, a2);
				}
			}
		}
	}

	private static void printResult(int a0, int a1, int a2) {
		BigDecimal res = calculate(a0, a1, a2);
		System.out.println("(" + a0 + "," + a1 + "," + a2 + ") = " + res + " " + res.remainder(bd_3));
	}

	private static BigDecimal calculate(int a0, int a1, int a2) {
		try {
			return bd_2.pow(a0).add(bd_n1).divide(bd_3).multiply(bd_2.pow(a1)).add(bd_n1).divide(bd_3)
					.multiply(bd_2.pow(a2)).add(bd_n1).divide(bd_3);
		} catch (Exception e) {
			return bd_n1;
		}
	}

	private static int permute(int[][] p_array, int n) {
		for (int i = 0, len = p_array[0].length; i < len; i++) {
			if (n == p_array[0][i]) {
				return p_array[1][i];
			}
		}
		return -1;
	}

	private static int permute(int[][] p_array, int n, int pow) {
		if (pow == 0) {
			return n;
		}

		int res = n;
		for (int i = 0; i < pow; i++) {
			res = permute(p_array, res);
		}
		return res;
	}
}
