package collatz.tree.function;

class R extends Function {

	public R() {
		super("r");
	}

	public final int eval(int m) {
		if ((m - 1) % 3 == 0) {
			return (m - 1) / 3;
		}
		return 0;
	}
}
