package collatz.dtsm.function;

@FunctionalInterface
public interface O {

	static O add = (s1, s2) -> {
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();

		int len = c1.length;
		char[] c = new char[len];
		for (int i = 0; i < len; i++) {
			if (c1[i] == 'p' && c2[i] == 'p') {
				c[i] = 'q';
			} else if (c1[i] == 'q' && c2[i] == 'q') {
				c[i] = 'p';
			} else if (c1[i] == 'p' && c2[i] == 'q') {
				c[i] = 'p';
			} else if (c1[i] == 'q' && c2[i] == 'p') {
				c[i] = 'p';
			} else if (c1[i] == 'q' && c2[i] == 'e') {
				c[i] = 'q';
			} else if (c1[i] == 'e' && c2[i] == 'q') {
				c[i] = 'q';
			} else if (c1[i] == 'p' && c2[i] == 'e') {
				c[i] = 'p';
			} else if (c1[i] == 'e' && c2[i] == 'p') {
				c[i] = 'p';
			} else {
				throw new RuntimeException("unspecified state");
			}
		}

		return new String(c);
	};

	String eval(String s1, String s2);
}
