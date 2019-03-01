package collatz.dtsm.util;

import java.util.List;

public class ByteUtils {

	private static final Byte BYTE_1 = Byte.valueOf((byte) 1);

	public static List<Byte> trim(List<Byte> bytes) {
		int start = bytes.indexOf(BYTE_1);
		int end = bytes.lastIndexOf(BYTE_1);

		if (end < 0 || end == start) {
			end = bytes.size();
		} else {
			end++;
		}

		return bytes.subList(start, end);
	}
}
