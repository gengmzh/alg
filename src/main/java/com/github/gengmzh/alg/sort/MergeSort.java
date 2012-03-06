package com.github.gengmzh.alg.sort;

/**
 * 归并排序
 * 
 * @since 2012-2-14
 * @author gmz
 * 
 */
public class MergeSort {

	public static void sort(int[] arr, int start, int length) {
		if (length == 1) {
			return;
		}
		int mid = length / 2;
		sort(arr, start, mid);
		sort(arr, start + mid, length - mid);
		merge(arr, start, start + mid - 1, start + length - 1);
	}

	public static void merge(int[] arr, int s, int m, int e) {
		int[] l = new int[m - s + 1], r = new int[e - m];
		System.arraycopy(arr, s, l, 0, l.length);
		System.arraycopy(arr, m + 1, r, 0, r.length);
		int i = 0, j = 0;
		while (i < l.length && j < r.length) {
			if (l[i] < r[j]) {
				arr[s + i + j] = l[i];
				i++;
			} else if (l[i] > r[j]) {
				arr[s + i + j] = r[j];
				j++;
			} else {
				arr[s + i + j] = l[i];
				i++;
				arr[s + i + j] = r[j];
				j++;
			}
		}
		while (i < l.length) {
			arr[s + i + j] = l[i++];
		}
		while (j < r.length) {
			arr[s + i + j] = r[j++];
		}
	}

	public static void main(String[] args) throws Exception {
		int[] arr = { 1, 4, 3, 2, 6, 7, 8, 2, 3, 1, 4, 23, 6, 7, 9 };
		sort(arr, 0, arr.length);
		for (int it : arr) {
			System.out.print(it + " ");
		}
	}

}
