package collatz.dtsm.function;

public class Composition_F {

	// a string like aabccab
	private final String compositionString;

	public Composition_F(long i) {
		this(toComposition(Long.toString(i, 3)));
	}
	
	public Composition_F(String compositionString) {
		this.compositionString = compositionString;
	}

	private String toTernary() {
		return toTernary(compositionString);
	}

	private static String toTernary(String composition) {
		return composition.replace('a', '0').replace('b', '1').replace('c', '2');
	}

	private static String toComposition(String ternary) {
		return ternary.replace('0', 'a').replace('1', 'b').replace('2', 'c');
	}

	public Composition_F add(Composition_F composition_f) {
		String ternary1 = toTernary();
		String ternary2 = composition_f.toTernary();
		int i1 = Integer.parseInt(ternary1, 3);
		int i2 = Integer.parseInt(ternary2, 3);
		String addedTernary = Integer.toString(i1 + i2, 3);
		return new Composition_F(toComposition(addedTernary));
	}

	public Composition_F multiply(Composition_F composition_f) {
		String ternary1 = toTernary();
		String ternary2 = composition_f.toTernary();
		int i1 = Integer.parseInt(ternary1, 3);
		int i2 = Integer.parseInt(ternary2, 3);
		String addedTernary = Integer.toString(i1 * i2, 3);
		return new Composition_F(toComposition(addedTernary));
	}

	public F f() {
		F f = new F() {

			@Override
			public String eval(String s) {
				char[] chars = compositionString.toCharArray();
				int len = chars.length;

				String result = s;
				for (int i = len - 1; i >= 0; i--) {
					switch (chars[i]) {
					case 'a':
						result = F.fa.eval(result);
						break;
					case 'b':
						result = F.fb.eval(result);
						break;
					case 'c':
						result = F.fc.eval(result);
						break;
					}
				}
				return result;
			}
		};
		return f;
	}

	public String toString() {
		return compositionString;
	}
}
