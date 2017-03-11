/**
 * 
 */
package com.github.myron.security;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author gengmaozhang01
 * @since 下午2:26:19
 */
public class SecurityTest {

	/**
	 * @author gengmaozhang01
	 * @since 下午2:26:22
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// properties
		System.out.println("properties:");
		String[] properties = { "os.name", "java.version", "java.home", "user.home", "java.ext.dirs" };
		for (String prop : properties) {
			String value = System.getProperty(prop);
			System.out.println(prop + " = " + value);
		}

		// read file
		System.out.println();
		System.out.println("read file:");
		String file = "E:\\download\\col_iplib.txt";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = reader.readLine();
		System.out.println("read line: " + line);
		reader.close();

		// write file
		System.out.println();
		System.out.println("write file:");
		file = "E:\\download\\security-test.txt";
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		line = "test java security";
		writer.write(line);
		writer.newLine();
		System.out.println("write line: " + line);
		writer.close();
	}

}
