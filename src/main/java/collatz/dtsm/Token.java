package collatz.dtsm;

import java.util.ArrayList;
import java.util.List;

import collatz.dtsm.util.ByteUtils;

public class Token {

	public State state = Dtsm.s;
	
	public int index = 0;
	
	public Token successor;
	
	public List<Byte> epsilonSequenceProduced = new ArrayList<>();
	
	public List<Byte> deltaSequencePassed = new ArrayList<>();
	
	public Token () {
	}
	
	public Token (State state) {
		this.state = state;
	}
	
	public void walk (Transition transition) {
		deltaSequencePassed.add(transition.inputDelta);
		epsilonSequenceProduced.add(transition.outputEpsilon);
		
		state = transition.to;
		index++;
	}
	
	public List<Byte> getEpsilonSequenceProduced() {
		return ByteUtils.trim(epsilonSequenceProduced);
	}
}
