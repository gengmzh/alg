/**
 * 
 */
package com.github.gengmzh.alg.sort;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @since 2012-3-27
 * @author gmz
 * 
 */
public class DualPivotQuicksortTest {

	@Test
	public void test_sort() {
		int[] arr = new int[] { 15, 1, 5, 3, 20, 13, 12, 2, 13, 4 };
		DualPivotQuicksort.sort(arr, 0, arr.length - 1);

		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				Assert.assertTrue(arr[i - 1] <= arr[i]);
			}
			System.out.print(arr[i] + " ");
		}
	}

}
