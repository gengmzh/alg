/**
 * 
 */
package com.github.gengmzh.alg.sort;

import junit.framework.Assert;

import org.junit.Test;

import com.github.gengmzh.alg.sort.CountingSort.IntItem;
import com.github.gengmzh.alg.sort.CountingSort.Item;

/**
 * @since 2012-3-6
 * @author gmz
 * 
 */
public class CountingSortTest {

	@Test
	public void test_sort() {
		Item[] arr = new IntItem[] { new IntItem(15), new IntItem(1), new IntItem(13), new IntItem(4) };
		int max = 5;
		CountingSort.sort(arr, max);

		Item pre = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (pre.value() < max) {
				Assert.assertTrue(pre.value() <= arr[i].value());
			}
			System.out.print(pre.value() + " ");
			pre = arr[i];
		}
		System.out.print(pre.value() + " ");
	}

}
