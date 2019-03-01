package collatz.dtsm;

import collatz.dtsm.function.Composition_F;

public class RingComputation {

	public final int x;
	
	public final int y;
	
	public final int z;
	
	public final String u;
	
	public final String v;
	
	public final Composition_F comp;

	public RingComputation(int x, int y, int z, String u, String v, Composition_F comp) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.u = u;
		this.v = v;
		this.comp = comp;
	}
}
