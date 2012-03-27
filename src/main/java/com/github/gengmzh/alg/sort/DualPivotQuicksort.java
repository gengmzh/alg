/**
 * 
 */
package com.github.gengmzh.alg.sort;

/**
 * @since 2012-3-27
 * @author gmz
 * 
 */
public class DualPivotQuicksort {

	public static void sort(int[] values, int p, int r) {
		if (p >= r) {
			return;
		}
		int[] q = partition(values, p, r);
		sort(values, p, q[0] - 1);
		sort(values, q[0] + 1, q[1] - 1);
		sort(values, q[1] + 1, r);
	}

	private static int[] partition(int[] values, int p, int r) {
		// valurs[r-1]作为p1，values[r]作为p2
		if (values[r] < values[r - 1]) {
			swap(values, r - 1, r);
		}
		int q1 = --p, q2 = q1;
		while (++p < r - 1) {
			if (values[p] < values[r - 1]) {
				swap(values, ++q2, p);
				swap(values, ++q1, q2);
			} else if (values[p] < values[r]) {
				swap(values, ++q2, p);
			}
		}
		swap(values, ++q2, r - 1);
		swap(values, ++q1, q2);
		swap(values, ++q2, r);
		return new int[] { q1, q2 };
	}

	private static void swap(int[] values, int l, int r) {
		int tmp = values[l];
		values[l] = values[r];
		values[r] = tmp;
	}

}
