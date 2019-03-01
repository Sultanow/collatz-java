package collatz.dtsm.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import collatz.dtsm.RingComputation;
import collatz.dtsm.function.F;

public class FileUtils {

	public static void writeToFile(String path, RingComputation[][] vertices) {

		try (Writer writer = new BufferedWriter(new FileWriter(path))) {
			int maxX = vertices.length;
			int maxY = vertices[0].length;
			int lines = maxX * maxY;

			for (int i = 0; i < lines; i++) {
				int x = i % maxX;
				int y = i / maxX;

				RingComputation rc = vertices[x][y];

				String line = rc.x + "\t" + rc.y + "\t" + rc.z;
				writer.write(line);
				writer.write(System.getProperty("line.separator"));

//				System.out.println(rc.x + " " + rc.y + " " + rc.z + " u = " + rc.u + " = "
//						+ StringUtils.toDecimal(StringUtils.reverse(rc.u)) + " v = " + rc.v + " = "
//						+ StringUtils.toDecimal(rc.v) + " " + rc.comp.toString());

			}
		} catch (IOException e) {
			System.out.println("Problem occurs when creating file " + path);
			e.printStackTrace();
		}
	}

	public static void writeToFile(String path, F f, String[] input) {

		try (Writer writer = new BufferedWriter(new FileWriter(path))) {
			int len = input.length;
			
			for (int i = 0; i < len; i++) {
				String eval = f.eval(input[i]);
				String line = i + "\t" + input[i] + "\t" + StringUtils.toDecimal(eval) + "\t" + eval;
				writer.write(line);
				writer.write(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			System.out.println("Problem occurs when creating file " + path);
			e.printStackTrace();
		}
	}
}
