package collatz.dtsm;

import java.util.ArrayList;
import java.util.List;

public class State {

	public final String name;

	public final Transition[] successors = new Transition[2];

	public final List<Token> tokens = new ArrayList<>();
	
	public State(String name) {
		this.name = name;
	}

	public State next(byte delta) {
		return successors[0].inputDelta == delta ? successors[0].to : successors[1].to;
	}

}
