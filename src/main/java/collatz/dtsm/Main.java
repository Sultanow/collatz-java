package collatz.dtsm;

import java.util.Arrays;

import org.apache.commons.math3.primes.Primes;

import collatz.dtsm.function.Composition_F;
import collatz.dtsm.function.F;
import collatz.dtsm.util.FileUtils;
import collatz.dtsm.util.StringUtils;

public class Main {

	public static void main(String[] args) {

		Token t = new Token();
		Dtsm.processAll(t, Arrays.asList((new Byte[] { 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0 })));

		System.out.println(t.deltaSequencePassed);
		while (t.successor != null) {
			t = t.successor;
			System.out.println(t.deltaSequencePassed);
		}

		// System.out.println(F.fc.eval("pqpppqpppppp"));
		// System.out.println(F.fc.eval("pppqpppqpppp"));
		// System.out.println(F.fc_inv.eval(F.fc.eval("pqpppqpppppp")));

		// associativity is obvious
		// https://math.stackexchange.com/questions/523906/show-that-function-compositions-are-associative
		/*
		 * System.out.println( F.fa_inv.eval( F.fa.eval( F.fc.eval("pqpppqpppppp") ) )
		 * );
		 */

		//System.out.println(MathUtils.toDecimal(StringUtils.toBinaryString(s)));
		//System.out.println(MathUtils.toDecimal(StringUtils.toBinaryString(F.fa.eval(s))));
		
		/*
		 *         [1,0,2]
		 * [5,2,0] [0,3,1] = [5,6,12]
		 *         [1,1,4]
		 */
		//INDArray nd = Nd4j.create(new float[]{5,2,0},new int[]{3});
		//INDArray nd2 = Nd4j.create(new float[]{1,0,2,0,3,1,1,1,4},new int[]{3, 3});
		//System.out.println(nd.mmul(nd2));
		
		int maxX = 13;
		int maxY = 13;
		
		//generate an array of primes
		int prime = 2;
		int[] primeArray = new int[2*maxX];
		for(int i=0; i<2*maxX; i++) {
			prime = Primes.nextPrime(prime);
			primeArray[i] = prime;
			prime++;
		}
		
		RingComputation[][] vertices = new RingComputation[maxX][maxY];
		
		for(int i=0; i<maxX; i++) {
			for(int j=0; j<maxY; j++) {
				//x
				int x = i*2 + 1;
				//int x = primeArray[2*i];
				//int x = (int) Math.pow(2, i);
				String u = StringUtils.reverse(StringUtils.toPqString(x));
				//y
				long y = 2*j;
				//int y = primeArray[j];
				Composition_F comp_f = new Composition_F(y);
				//z
				String v = comp_f.f().eval(u);
				int z = StringUtils.toDecimal(StringUtils.reverse(v));
				vertices[i][j] = new RingComputation(i, j, z, u, v, comp_f);
			}
		}
		
		FileUtils.writeToFile("c:\\tmp\\vertices.txt", vertices);
		
		int len = 10;
		String[] input = new String[len];
		for (int i = 0; i<len; i++) {
			input[i] = StringUtils.toPqString(i);
		}
		FileUtils.writeToFile("c:\\tmp\\vertices_fa.txt", F.fa, input);
		FileUtils.writeToFile("c:\\tmp\\vertices_fb.txt", F.fb, input);
		FileUtils.writeToFile("c:\\tmp\\vertices_fc.txt", F.fc, input);
		
//		String u = "pqpppqpppppp";
//		String v = F.fc.eval(u);
//		System.out.println("u = "+ StringUtils.toDecimal(StringUtils.compact(StringUtils.reverse(u))) + " v = " + StringUtils.toDecimal("q"+StringUtils.reverse(v)));
		
		System.out.println(F.ft.eval(F.fs_inv.eval("qpp")));
		
		String u = "pqpppqpppppp";
		String v = F.fc.eval(u);
		System.out.println("u = "+ StringUtils.toDecimal(StringUtils.compact(StringUtils.reverse(u))) + " v = " + v + " = " + StringUtils.toDecimal(v) + " v_reversed = " + StringUtils.reverse(v) + " = " + StringUtils.toDecimal("q" + StringUtils.reverse(v)));
		
//		Composition_F cf1 = new Composition_F("abcbbbbcbbbabbbca");
//		Composition_F cf2 = new Composition_F("bbccac");
//		Composition_F cf3 = new Composition_F("cabc");
//		
//		System.out.println(cf3.multiply(cf1.add(cf2)).f().eval("pqqqpppqp") + " " + cf3.multiply(cf1).add(cf3.multiply(cf2)).f().eval("pqqqpppqp"));
		
		
	}
}
