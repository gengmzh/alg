/**
 * 
 */
package com.github.gengmzh.hadoop.terasort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

/**
 * @since 2012-5-9
 * @author gmz
 * 
 */
public class DataBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String file = "d:\\test.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		long total = 10000000;
		while (total-- > 0) {
			writer.write(String.valueOf(new Random().nextLong()));
			writer.newLine();
		}
		writer.close();
	}

}
