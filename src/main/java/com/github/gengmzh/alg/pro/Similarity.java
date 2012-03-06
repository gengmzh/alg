/**
 * 
 */
package com.github.gengmzh.alg.pro;

/**
 * @since 2012-2-19
 * @author gmz
 * 
 */
public class Similarity {

	// euclidean distance score
	// [0,1], 1 means the same
	public static double distance(double[] it1, double[] it2) {
		double sum = 0.00d;
		for (int i = 0; i < it1.length && i < it2.length; i++) {
			sum += Math.pow(it1[i] - it2[i], 2);
		}
		return 1 / (1 + Math.sqrt(sum));
	}

	// cosine of two vector
	// [-1,1] where 1 means the same
	public static double cosine(double[] it1, double[] it2) {
		double prod = 0.00d, dis1 = 0.00d, dis2 = 0.00d;
		for (int i = 0; i < it1.length && i < it2.length; i++) {
			prod += it1[i] * it2[i];
			dis1 += Math.pow(it1[i], 2);
			dis2 += Math.pow(it2[i], 2);
		}
		return dis1 == 0 || dis2 == 0 ?1.00d: prod
				/ (Math.sqrt(dis1*dis2)) ;
	}

	// pearson coefficient
	// [-1,1] where 1 means the same
	public static double pearson(double[] it1, double[] it2) {
		double s1 = 0d, s2 = 0d, p1 = 0d, p2 = 0d, pr = 0d;
		int n = Math.min(it1.length, it2.length);
		for (int i = 0; i < n; i++) {
			s1 += it1[i];
			s2 += it2[i];
			p1 += Math.pow(it1[i], 2);
			p2 += Math.pow(it2[i], 2);
			pr += it1[i] * it2[i];
		}
		double den = Math.sqrt((p1 - Math.pow(s1, 2) / n)
				* (p2 - Math.pow(s2, 2) / n));
		if (den == 0) {
			return 0.00d;
		}
		double sub = pr - s1 * s2 / n;
		return sub / den;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[] it1 = { 1, 2,3,4 };
		double[] it2 = {  1,2,4,3 };

		double score = distance(it1, it2);
		System.out.println("distance: " + score);

		score = cosine(it1, it2);
		System.out.println("cosine: " + score);

		score = pearson(it1, it2);
		System.out.println("pearson: " + score);
	}
}
