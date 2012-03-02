/**
 * 
 */
package com.aihu001.alg.sort;

import java.util.Comparator;

import org.junit.Assert;

/**
 * @since 2012-2-20
 * @author gmz
 * 
 */
public class HeapSort {

	/**
	 * 堆排序，只排出最大n个元素
	 */
	public static <T extends Comparable<T>> void sort(T[] values, int n) {
		build(values);
		int size = values.length;
		while (size-- > 0 && n-- > 0) {
			T tmp = values[0];
			values[0] = values[size];
			values[size] = tmp;
			bubble(values, 0, size);
		}
	}

	private static <T extends Comparable<T>> void build(T[] values) {
		for (int i = values.length / 2 - 1; i >= 0; i--) {
			bubble(values, i, values.length);
		}
	}

	private static <T extends Comparable<T>> void bubble(T[] values, int i, int size) {
		if (i >= size / 2) {
			return;
		}
		int max = (i << 1) + 1, r = (i << 1) + 2;
		if (r < size && values[max].compareTo(values[r]) < 0) {
			max = r;
		}
		if (values[i].compareTo(values[max]) < 0) {
			T tmp = values[i];
			values[i] = values[max];
			values[max] = tmp;
			bubble(values, max, size);
		}
	}

	/**
	 * 堆排序，只排出最大n个元素
	 */
	public static <T> void sort(T[] values, Comparator<T> comparator, int n) {
		build(values, comparator);
		int size = values.length;
		while (size-- > 0 && n-- > 0) {
			T tmp = values[0];
			values[0] = values[size];
			values[size] = tmp;
			bubble(values, comparator, 0, size);
		}
	}

	private static <T> void build(T[] values, Comparator<T> comparator) {
		for (int i = values.length / 2 - 1; i >= 0; i--) {
			bubble(values, comparator, i, values.length);
		}
	}

	private static <T> void bubble(T[] values, Comparator<T> comparator, int i, int size) {
		if (i >= size / 2) {
			return;
		}
		int max = (i << 1) + 1, r = (i << 1) + 2;
		if (r < size && comparator.compare(values[max], values[r]) < 0) {
			max = r;
		}
		if (comparator.compare(values[i], values[max]) < 0) {
			T tmp = values[i];
			values[i] = values[max];
			values[max] = tmp;
			bubble(values, comparator, max, size);
		}
	}

	public static void main(String[] args) {
		Integer[] arr = { 4, 3, 3, 5, 2, 1, 9, 4, 7, 8 };
		int topN = 5;
		HeapSort.sort(arr, topN);

		Integer last = null, cur = null;
		for (int i = arr.length - 1; i >= 0; i--) {
			cur = arr[i];
			if (last != null) {
				Assert.assertTrue(cur <= last);
			}
			if (i > arr.length - 1 - topN) {
				last = cur;
			}
			System.out.print(cur + ", ");
		}
		System.out.println();
	}

}
