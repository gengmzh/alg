/**
 * 
 */
package com.aihu001.alg.sort.optimal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 最优解问题
 * 
 * @since 2012-2-18
 * @author gmz
 * 
 */
public class Optimizer {

	static int[] dorm = { 0, 1, 2, 3, 4 };
	static int[][] stu = { { 0, 2 }, { 1, 3 }, { 1, 4 }, { 2, 3 }, { 4, 2 },
			{ 2, 1 }, { 0, 1 }, { 1, 5 }, { 3, 1 }, { 2, 4 } };

	public static int cost(int[] sol) {
		int cost = 0;
		for (int i = 0; i < sol.length; i++) {
			cost += (sol[i] == stu[i][0] ? 0 : (sol[i] == stu[i][1] ? 1 : 2));
		}
		return cost;
	}

	public static int[] randSol() {
		List<Integer> sol = Arrays.asList(new Integer[] { 0, 1, 2, 3, 4, 0, 1,
				2, 3, 4 });
		Collections.shuffle(sol);
		int[] res = new int[10];
		for (int i = 0; i < 10; i++) {
			res[i] = sol.get(i);
		}
		return res;
	}

	public static void randOptimize() {
		int[] res = randSol();
		int cost = cost(res);
		int max = 10000;
		while (max-- >= 0) {
			int[] rd = randSol();
			int cs = cost(rd);
			if (cs < cost) {
				res = rd;
				cost = cs;
			}
		}
		System.out.print("random " + cost + ": ");
		for (int r : res) {
			System.out.print(r + ", ");
		}
		System.out.println();
	}

	public static void hillOptimize() {
		int[] sol = randSol();
		int cost = cost(sol);
		while (true) {
			int[] ls = null;
			int lc = 0;
			for (int i = 0; i < 10; i++) {
				// int i= new Random().nextInt(10);
				int[] s = new int[10]; 
					System.arraycopy(sol, 0, s, 0, 10); 
					int j=(i+5)%10;
					int t=s[i];s[i]=s[j];s[j]=t;
					int c = cost(s);
				if (ls == null || c < lc) {
					ls = s;
					lc = c;
				}
			}
			if (lc < cost) {
				sol = ls;
				cost = lc;
			} else {
				break;
			}
		}
		System.out.print("hill " + cost + ": ");
		for (int s : sol) {
			System.out.print(s + ", ");
		}
		System.out.println();
	}

	public static void gen() {
		int[][] pop = new int[10][10];
		int[] cost = new int[10];
		for (int i = 0; i < 10; i++) {
			pop[i] = randSol();
			cost[i] = cost(pop[i]);
		}

		int size = 1000;
		while (size-- >= 0) {
			for (int i = 1; i < 10; i++) {
				int min = cost[i];
				int[] p = pop[i];
				int j = i - 1;
				for (; j >= 0; j--) {
					if (cost[j] > min) {
						cost[j + 1] = cost[j];
						pop[j + 1] = pop[j];
					} else
						break;
				}
				cost[j + 1] = min;
				pop[j + 1] = p;
			}

			// int[][] np=new int[5][10];
			// int[] nc=new int[10];
			for (int i = 0; i < 5; i++) {
				int d = new Random().nextInt(10);
				// int[] p=new int[10];
//				if (d > 0)
					System.arraycopy(pop[i], 0, pop[i + 5], 0, 10);
				int j=(d+5)%10;
				int t=pop[i+5][d];pop[i+5][d]=pop[i+5][j];pop[i+5][j]=t;
//				if (d < 9)
//					System.arraycopy(pop[i], d + 1, pop[i + 5], d + 1, 9 - d);
//				pop[i + 5][d] = (pop[i][d] + 1) % 5;
				cost[i + 5] = cost(pop[i + 5]);
			}

		}
		System.out.print("gen " + cost[0] + ": ");
		for (int s : pop[0]) {
			System.out.print(s + ", ");
		}
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		randOptimize();
		hillOptimize();

		gen();
	}

}
