/**
 * 
 */
package com.aihu001.alg.sort;

/**
 * ¶ÑÅÅÐò
 * 
 * @since 2012-2-18
 * @author gmz
 * 
 */
public class Heapsort {

	public static void sortMax(int[] arr, int i, int size) {
		if (i >= (size >> 1)) {
			return;
		}
		int l = (i << 1)+1, r = (i << 1) + 2;
		if (r < size && arr[l] < arr[r]) {
			l = r;
		}
		if (arr[i] >= arr[l]) {
			return;
		}
		int tmp = arr[i];
		arr[i] = arr[l];
		arr[l] = tmp;
		sortMax(arr, l, size);
	}

	public static void build(int[] arr) {
		for (int i = ((arr.length >> 1) - 1); i >= 0; i--) {
			sortMax(arr, i, arr.length);
		}
	}

	public static void sort(int[] arr) {
		build(arr);
		int size = arr.length ;
		while (size-- > 1) {
			int max = arr[0];
			arr[0] = arr[size ];
			arr[size ] = max;
			sortMax(arr, 0, size);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = { 4, 6, 7, 2, 3, 9, 7, 8, 2, 4,23,45,2,3,4,5,67,0 };
		sort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
	}

}
