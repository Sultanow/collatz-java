package collatz.dtsm;

public class Transition {

	public final State from;
	
	public final State to;
	
	public final byte inputDelta;

	public final byte outputEpsilon;

	public Transition(State from, State to, byte inputDelta, byte outputEpsilon) {
		this.from = from;
		this.to = to;
		this.inputDelta = inputDelta;
		this.outputEpsilon = outputEpsilon;
		
		if (from.successors[0] == null) {
			from.successors[0] = this;
		} else {
			from.successors[1] = this;
		}
	}
}
