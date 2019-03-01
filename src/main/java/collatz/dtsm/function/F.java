package collatz.dtsm.function;

import collatz.dtsm.Dtsm;

@FunctionalInterface
public interface F {
	
	static F ft = (s) -> {return "q" + s;};
	static F fs = (s) -> {return Dtsm.function_state(s, Dtsm.s);};
	static F fa = (s) -> {return Dtsm.function_state(s, Dtsm.a);};
	static F fb = (s) -> {return Dtsm.function_state(s, Dtsm.b);};
	static F fc = (s) -> {return Dtsm.function_state(s, Dtsm.c);};
	static F fs_inv = (s) -> {return Dtsm.function_state_inverse(s, Dtsm.s);};
	static F fa_inv = (s) -> {return Dtsm.function_state_inverse(s, Dtsm.a);};
	static F fb_inv = (s) -> {return Dtsm.function_state_inverse(s, Dtsm.b);};
	static F fc_inv = (s) -> {return Dtsm.function_state_inverse(s, Dtsm.c);};
	
	String eval(String s);
}
