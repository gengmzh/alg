/**
 * 
 */
package com.github.gengmzh.alg.sort;


/**
 * @since 2012-3-6
 * @author gmz
 * 
 */
public class CountingSort {

	/**
	 * stable counting sort the values which value is lte max
	 * 
	 * @param values
	 * @param max
	 */
	public static void sort(Item[] values, int max) {
		int[] cnt = new int[max];
		for (Item item : values) {
			cnt[index(item, max)]++;
		}
		for (int i = 1; i < max; i++) {
			cnt[i] += cnt[i - 1];
		}
		Item[] tmp = new Item[values.length];
		for (int i = values.length - 1; i >= 0; i--) {
			int idx = --cnt[index(values[i], max)];
			tmp[idx] = values[i];
		}
		System.arraycopy(tmp, 0, values, 0, values.length);
	}

	private static int index(Item item, int max) {
		int value = item.value();
		return (value <= max ? value : max) - 1;
	}

	public static interface Item {
		public int value();
	}

	public static class IntItem implements Item {
		private int value;

		public IntItem(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}

}
