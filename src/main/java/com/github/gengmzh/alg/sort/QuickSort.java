/**
 * 
 */
package com.github.gengmzh.alg.sort;

import java.util.Comparator;

import junit.framework.Assert;

/**
 * @since 2012-3-2
 * @author gmz
 * 
 */
public class QuickSort {

	public static <T extends Comparable<T>> void sort(T[] values, int p, int r) {
		p = Math.max(p, 0);
		r = Math.min(r, values.length - 1);
		if (p >= r) {
			return;
		}
		int q = partition(values, p, r);
		sort(values, p, q);
		sort(values, q + 1, r);
	}

	private static <T extends Comparable<T>> int partition(T[] values, int p, int r) {
		swap(values, p, r);
		int q = --p;
		while (++p < r) {
			if (values[p].compareTo(values[r]) < 0) {
				swap(values, ++q, p);
			}
		}
		swap(values, ++q, r);
		return q;
	}

	public static <T> void sort(T[] values, Comparator<T> comparator, int p, int r) {
		p = Math.max(p, 0);
		r = Math.min(r, values.length - 1);
		if (p >= r) {
			return;
		}
		int q = partition(values, comparator, p, r);
		sort(values, comparator, p, q);
		sort(values, comparator, q + 1, r);
	}

	private static <T> int partition(T[] values, Comparator<T> comparator, int p, int r) {
		swap(values, p, r);
		int q = --p;
		while (++p < r) {
			if (comparator.compare(values[p], values[r]) < 0) {
				swap(values, ++q, p);
			}
		}
		swap(values, ++q, r);
		return q;
	}

	private static <T> void swap(T[] values, int l, int r) {
		T tmp = values[l];
		values[l] = values[r];
		values[r] = tmp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] arr = { 1, 5, 3, 2, 4, 6, 8, 7, 9, 1, 10, 5, 7 };
		int p = 0, r = arr.length - 1;
		sort(arr, p, r);
		int pre = arr[p];
		for (int i = p; i <= r; i++) {
			Assert.assertTrue(pre <= arr[i]);
			pre = arr[i];
			System.out.print(pre + " ");
		}
		System.out.println();

		arr = new Integer[] { 1, 5, 3, 2, 4, 6, 8, 7, 9, 1, 10, 5, 7 };
		p = 0;
		r = arr.length - 1;
		sort(arr, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			};
		}, p, r);
		pre = arr[p];
		for (int i = p; i <= r; i++) {
			Assert.assertTrue(pre <= arr[i]);
			pre = arr[i];
			System.out.print(pre + " ");
		}
		System.out.println();
	}
}
