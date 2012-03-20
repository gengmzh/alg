/**
 * 
 */
package com.github.gengmzh.euler;

/**
 * @since 2012-3-20
 * @author gmz
 * 
 */
public class Problem2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 1, b = 2, s = 0;
		while (b < 4000000) {
			if (b % 2 == 0) {
				s += b;
			}
			a = a + b;
			a = a + b;
			b = a - b;
			a = a - b;
		}
		System.out.println(s);
	}

}
