package collatz.tree.function;


public abstract class Function {

	public final static Function q = new Q();
	public final static Function r = new R();

	public String name;

	public Function left = null;
	public Function right = null;

	public void populate() {
		if (!name.startsWith("r") && r.compose(this).eval(1) >= 4) {
			left = r.compose(this);
		}
		right = q.compose(this);
	}

	public Function(String name) {
		this.name = name;
	}

	public final Function compose(final Function f) {
		String name = this.name + f.name;

		return new Function(name) {
			public final int eval(int m) {
				return Function.this.eval(f.eval(m));
			}
		};
	}

	public abstract int eval(int m);
}
