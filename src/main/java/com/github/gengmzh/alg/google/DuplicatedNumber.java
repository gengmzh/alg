/**
 * 
 */
package com.github.gengmzh.alg.google;

/**
 * @author mzhgeng
 * 
 */
public class DuplicatedNumber {

	public static void findDuplicated(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == i || arr[i] == -1) {
				continue;
			}
			int tmp = arr[i];
			arr[i] = -1;
			while (arr[tmp] != tmp) {
				int t = arr[tmp];
				arr[tmp] = tmp;
				if (t == -1) {
					tmp = -1;
					break;
				} else {
					tmp = t;
				}
			}
			if (tmp != -1) {
				System.out.println(tmp);
			}
		}
		for (int a : arr) {
			System.out.print(a + " ");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		findDuplicated(new int[] { 1, 0, 2, 2, 2, 5 });
	}

}
