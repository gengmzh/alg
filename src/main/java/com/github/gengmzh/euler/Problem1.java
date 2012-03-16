/**
 * 
 */
package com.github.gengmzh.euler;

import junit.framework.Assert;

/**
 * @since 2012-3-16
 * @author gmz
 * 
 */
public class Problem1 {

	public static long sumMultiple(long n, long... factors) {
		long sum = 0;
		for (long i = 1; i < n; i++) {
			for (long f : factors) {
				if (i % f == 0) {
					sum += i;
					break;
				}
			}
		}
		return sum;
	}

	public static long sumMultipleOfThreeAndFive(long n) {
		long sum = 0;
		long d = (n - 1) / 3;
		sum += 3 * d * (1 + d) / 2;
		d = (n - 1) / 5;
		sum += 5 * d * (1 + d) / 2;
		d = (n - 1) / 15;
		sum -= 15 * d * (1 + d) / 2;
		return sum;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long sum = sumMultiple(10, 3, 5);
		Assert.assertEquals(sum, 23);
		System.out.println(sumMultiple(1000, 3, 5));

		sum = sumMultipleOfThreeAndFive(10);
		Assert.assertEquals(sum, 23);
		System.out.println(sumMultipleOfThreeAndFive(1000));
	}

}
