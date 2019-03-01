package collatz.dtsm;

import java.util.List;

import collatz.dtsm.util.ByteUtils;
import collatz.dtsm.util.StringUtils;

public class Dtsm {

	public static final State s = new State("s");
	public static final State a = new State("a");
	public static final State b = new State("b");
	public static final State c = new State("c");

	private static final Transition s_s = new Transition(s, s, (byte) 0, (byte) 0);
	private static final Transition s_c = new Transition(s, c, (byte) 1, (byte) 0);
	private static final Transition c_c = new Transition(c, c, (byte) 1, (byte) 1);
	private static final Transition c_b = new Transition(c, b, (byte) 0, (byte) 0);
	private static final Transition b_c = new Transition(b, c, (byte) 1, (byte) 0);
	private static final Transition b_a = new Transition(b, a, (byte) 0, (byte) 1);
	private static final Transition a_a = new Transition(a, a, (byte) 0, (byte) 0);
	private static final Transition a_b = new Transition(a, b, (byte) 1, (byte) 1);

	private static final Transition[] transitions = { s_s, s_c, c_c, c_b, b_c, b_a, a_a, a_b };

	public static void processAll(Token t, List<Byte> inputDeltas) {

		Token temp = t;
		inputDeltas = ByteUtils.trim(inputDeltas);

		while (inputDeltas.size() > 1) {
			process(temp, inputDeltas);

			inputDeltas = temp.getEpsilonSequenceProduced();
			temp.successor = new Token();
			temp = temp.successor;
		}
	}

	public static void process(Token t, List<Byte> inputDeltas) {
		for (byte b : inputDeltas) {
			process(t, b);
		}
		while (t.state != a) {
			process(t, (byte) 0);
		}
	}

	public static void process(Token t, byte inputDelta) {
		t.walk(findByDelta(t, inputDelta));
	}

	public static Transition findByDelta(Token t, byte delta) {
		for (Transition transition : transitions) {
			if (transition.from == t.state && transition.inputDelta == delta) {
				return transition;
			}
		}
		return null;
	}

	public static Transition findByEpsilon(Token t, byte epsilon) {
		for (Transition transition : transitions) {
			if (transition.from == t.state && transition.outputEpsilon == epsilon) {
				return transition;
			}
		}
		return null;
	}

	public static String function_state(String input, State s) {
		Token t = new Token(s);
		for (byte b : StringUtils.toBytes(input)) {
			t.walk(findByEpsilon(t, b));
		}
		return StringUtils.toPqString(t.deltaSequencePassed);
	}
	
	public static String function_state_inverse(String input, State s) {
		Token t = new Token(s);
		for (byte b : StringUtils.toBytes(input)) {
			t.walk(findByDelta(t, b));
		}
		return StringUtils.toPqString(t.epsilonSequenceProduced);
	}
}
