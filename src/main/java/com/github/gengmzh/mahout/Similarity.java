/**
 * 
 */
package com.github.gengmzh.mahout;

import java.io.File;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * @since 2012-5-10
 * @author gmz
 * 
 */
public class Similarity {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DataModel data = new FileDataModel(new File("C:\\Users\\gmz\\AppData\\Local\\Temp\\ratings.txt"));

		UserSimilarity similarity = new PearsonCorrelationSimilarity(data);
		double value = similarity.userSimilarity(1L, 2L);
		System.out.println(value);

		similarity = new UncenteredCosineSimilarity(data);
		value = similarity.userSimilarity(1L, 2L);
		System.out.println(value);

		similarity = new EuclideanDistanceSimilarity(data);
		value = similarity.userSimilarity(1L, 2L);
		System.out.println(value);

	}

}
