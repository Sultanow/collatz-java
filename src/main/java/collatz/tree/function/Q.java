package collatz.tree.function;

public class Q extends Function {

	public Q() {
		super("q");
	}

	public final int eval(int m) {
		return m * 2;
	}
}
