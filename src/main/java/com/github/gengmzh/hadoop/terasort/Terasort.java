/**
 * 
 */
package com.github.gengmzh.hadoop.terasort;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @since 2012-5-9
 * @author gmz
 * 
 */
public class Terasort extends Configured implements Tool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	@Override
	public int run(String[] args) throws Exception {
		Job job = new Job(getConf(), "terasort");
		job.setJarByClass(getClass());

		Path input = new Path(args[0]);
		FileInputFormat.setInputPaths(job, input);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(NullWritable.class);

		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);

		// sample
		job.setPartitionerClass(TotalOrderPartitioner.class);
		Path pfile = new Path(input, "_partitions");
		TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), pfile);
		InputSampler.Sampler<LongWritable, NullWritable> sampler = new InputSampler.RandomSampler<LongWritable, NullWritable>(
				0.1, 10000);
		InputSampler.writePartitionFile(job, sampler);
		URI partitionUri = new URI(pfile.toString() + "#_partitions");
		DistributedCache.addCacheFile(partitionUri, getConf());
		DistributedCache.createSymlink(getConf());

		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class MyMapper extends Mapper<Text, Text, LongWritable, NullWritable> {
		protected void map(Text key, Text value,
				org.apache.hadoop.mapreduce.Mapper<Text, Text, LongWritable, NullWritable>.Context context)
				throws java.io.IOException, InterruptedException {
			try {
				LongWritable lw = new LongWritable(Long.valueOf(key.toString()));
				context.write(lw, NullWritable.get());
			} catch (Exception e) {
				// TODO: handle exception
			}
		};
	}

	public static class MyReducer extends Reducer<LongWritable, NullWritable, LongWritable, NullWritable> {
		protected void reduce(LongWritable arg0, java.lang.Iterable<NullWritable> arg1,
				org.apache.hadoop.mapreduce.Reducer<LongWritable, NullWritable, LongWritable, NullWritable>.Context arg2)
				throws java.io.IOException, InterruptedException {
			arg2.write(arg0, NullWritable.get());
		};
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new Terasort(), args);
		System.exit(res);
	}

}
