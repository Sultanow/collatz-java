package collatz.dtsm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import collatz.dtsm.PermutationKey;

public class StringUtils {

	public static Pattern COMPACT_PQ_STRING = Pattern.compile("(q[p,q]*q)");
	
	public static String compact(String s) {
		Matcher m = COMPACT_PQ_STRING.matcher(s);
		if (m.find()) {
			return m.group(1);
		}
		return s;
	}
	
	public static String reverse(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	public static float[] toIntArray(String m) {
		float[] result = new float[m.length()];
		int i = 0;
		for (char c : m.toCharArray()) {
			result[i++] = c == 'p' ? 0 : 1;
		}
		return result;
	}

	public static List<Byte> toBytes(String m) {
		List<Byte> bytes = new ArrayList<Byte>();
		for (char c : m.toCharArray()) {
			if (c == 'e') {
				continue;
			}
			bytes.add(c == 'p' ? (byte) 0 : (byte) 1);
		}
		Collections.reverse(bytes);
		return bytes;
	}

	public static int toDecimal(String s) {
		//return MathUtils.toDecimal(toBinaryString(s));
		return Integer.parseInt(toBinaryString(s), 2);
	}

	public static String toBinaryString(String s) {
		int len = s.length();
		char[] chars = new char[len];
		int i = 0;
		for (char c : s.toCharArray()) {
			if (c == 'e') {
				continue;
			}
			chars[len - 1 - (i++)] = c == 'p' ? '0' : '1';
		}
		return new String(chars);
	}

	public static String toPqString(int i) {
		return Integer.toBinaryString(i).replace('0', 'p').replace('1', 'q');
	}

	public static String toPqString(List<Byte> bytes) {
		int len = bytes.size();
		byte[] byteArray = new byte[len];
		for (int i = 0; i < len; i++) {
			byteArray[i] = bytes.get(i);
		}
		return toPqString(byteArray);
	}

	public static String toPqString(byte[] bytes) {
		int len = bytes.length;
		char[] chars = new char[len];
		int i = 0;
		for (byte b : bytes) {
			chars[len - 1 - (i++)] = b == 0 ? 'p' : 'q';
		}
		return new String(chars);
	}

	private static void swap(int[] orig, char[] a, int i, int j) {
		char ch = a[i];
		a[i] = a[j];
		a[j] = ch;

		int k = orig[i];
		orig[i] = orig[j];
		orig[j] = k;
	}

	public static Map<PermutationKey, String> getPermutations(String s) {
		Map<PermutationKey, String> permutations = new HashMap<>();
		int len = s.length();

		int[] orig = new int[len];
		for (int i = 0; i < len; i++) {
			orig[i] = i + 1;
		}
		permutations.put(new PermutationKey(orig), s);
		int[] corig = orig.clone();

		char[] chars = s.toCharArray();
		// Weight index control array
		int[] p = new int[s.length()];
		// i, j represents upper and lower bound index resp. for swapping
		int i = 1, j = 0;

		while (i < s.length()) {
			if (p[i] < i) {
				// if i is odd then j = p[i], otherwise j = 0
				j = (i % 2) * p[i];

				// swap(a[j], a[i])
				swap(corig, chars, i, j);
				permutations.put(new PermutationKey(corig), String.valueOf(chars));
				corig = corig.clone();
				// increase index "weight" for i by one
				p[i]++;
				// reset index i to 1
				i = 1;
			} else {
				// otherwise p[i] == i
				p[i] = 0;
				i++;
			}
		}

		return permutations;
	}

	public static void printPermutation(Entry<PermutationKey, String> entry) {
		StringJoiner joiner = new StringJoiner(",");
		IntStream.of(entry.getKey().order).forEach(i -> joiner.add(String.valueOf(i)));

		System.out.println("(" + joiner.toString() + ")->(" + entry.getValue() + ")");
	}

	public static void printPermutations(Map<PermutationKey, String> map) {
		for (Entry<PermutationKey, String> entry : map.entrySet()) {
			printPermutation(entry);
		}
	}

	public static List<String> generateCombinations(int size, char[] chars) {
		List<String> result = new ArrayList<>();

		int carry;
		int[] indices = new int[size];
		do {
			int j = 0;
			char[] string = new char[size];
			for (int index : indices) {
				string[j++] = chars[index];
			}
			result.add(new String(string));

			carry = 1;
			for (int i = indices.length - 1; i >= 0; i--) {
				if (carry == 0)
					break;

				indices[i] += carry;
				carry = 0;

				if (indices[i] == chars.length) {
					carry = 1;
					indices[i] = 0;
				}
			}
		} while (carry != 1);

		return result;
	}
}
