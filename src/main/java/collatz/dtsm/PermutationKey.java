package collatz.dtsm;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class PermutationKey {

	public final int[] order;

	public PermutationKey(int[] order) {
		super();
		this.order = order;
	}

	public static String toString(PermutationKey permutationKey) {
		StringJoiner joiner = new StringJoiner(",");
		IntStream.of(permutationKey.order).forEach(i -> joiner.add(String.valueOf(i)));
		return joiner.toString();
	}
	
	@Override
	public String toString() {
		return PermutationKey.toString(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(order);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermutationKey other = (PermutationKey) obj;
		if (!Arrays.equals(order, other.order))
			return false;
		return true;
	}
}
