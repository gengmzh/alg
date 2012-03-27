/**
 * 
 */
package com.github.gengmzh.jdk7;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @since 2012-3-27
 * @author gmz
 * 
 */
public class LanguageTest {

	@Test
	public void test_binary_literal() throws Exception {
		byte bin = (byte) 0b00000010;
		Assert.assertEquals(bin, 2);
	}

	@Test
	public void test_underscore_in_number() throws Exception {
		int seq = 100_100_100;
		Assert.assertEquals(seq, 100100100);
	}

	@Test
	public void test_switch_string() throws Exception {
		String key = "tue";
		int week = 0;
		switch (key) {
		case "mon":
			week = 1;
			break;
		case "tue":
			week = 2;
			break;
		case "wed":
			week = 3;
			break;
		case "thr":
			week = 4;
			break;
		case "fri":
			week = 5;
			break;
		case "sat":
			week = 6;
			break;
		case "sun":
			week = 7;
			break;
		default:
			break;
		}
		Assert.assertEquals(week, 2);
	}

	static class Generic<T> {
		T title = null;
		Object value = null;

		<V> Generic(T t, V v) {
			title = t;
			value = v;
		}

		public T getTitle() {
			return title;
		}

		public Object getValue() {
			return value;
		}
	}

	@Test
	public void test_generic() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("str001");
		list.add("str002");
		Assert.assertTrue(list.contains("str001"));

		Generic<String> gen = new Generic<>("g", 10000L);
		Assert.assertEquals("g", gen.getTitle());
		Assert.assertEquals(10000L, gen.getValue());
	}

	@Test
	public void test_try_resource() throws Exception {
		URL url = getClass().getResource("./");
		String file = url.getPath() + File.separator + "test.txt";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write("line one");
			writer.newLine();
		}

		new File(file).delete();
	}

	@Test
	public void test_catch() throws Exception {
		int i = 10;
		try {
			if (i < 1) {
				throw new IllegalArgumentException("i<1");
			}
			if (i < 100) {
				throw new IllegalStateException("i<100");
			}
		} catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}

	}

//	@Test
//	public void test_throws() throws IllegalStateException {
//		int i = 10;
//		try {
//			if (i < 100) {
//				throw new IllegalStateException("i<100");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}

}
