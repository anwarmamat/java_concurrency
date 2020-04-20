package wordCount;

import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Word count implementation in hadoop.  The following code is adapted from
 * 
 * https://hadoop.apache.org/docs/r1.2.1/mapred_tutorial.html#Source+Code
 * using ideas for repairing the above from
 * http://kickstarthadoop.blogspot.com/2011/04/word-count-hadoop-map-reduce-
 * example.html
 * 
 *
 */
public class WordCount {

	public static void main(String[] args) throws Exception {

		// Set up and configure MapReduce job.

		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJobName("WordCount");
		job.setJarByClass(WordCount.class); // In Eclipse this will not create JAR file

		// Set key, output classes for the job.
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// Set Mapper and Reducer classes for the job.
		job.setMapperClass(MapClass.class);
		job.setReducerClass(ReduceClass.class);

		// Set format of input files. "TextInputFormat" views files as
		// a sequence of lines; each line is a value, with the key associated
		// with the value being the position of the first character of the 
		// line within the file.
		job.setInputFormatClass(TextInputFormat.class);

		// Sets format of output files: here, lines of text.  By default, each
		// key / value pair produced by reduce is converted into a single line
		// of text, with key and value separated by a tab.  toString() methods
		// for the key and  value types are called.
		job.setOutputFormatClass(TextOutputFormat.class);

		// Set paths for location of input, output. Note former is assumed to be
		// initial command-line argument, while latter is second. No error-checking
		// is performed on this, so there is a GenericOptionsParser warning when
		// run.  Note that if the output path exists, an error will be generated.

		//TextInputFormat.setInputPaths(job, new Path(args[0]));
		//TextOutputFormat.setOutputPath(job, new Path(args[1]));

		TextInputFormat.setInputPaths(job, new Path("textInputs"));
		TextOutputFormat.setOutputPath(job, new Path("textOutputs"));

		
		// Run job
		Date startTime = new Date();
		System.out.println("Job started: " + startTime);
		boolean success = job.waitForCompletion(true);
		if (success) {
			Date endTime = new Date();
			System.out.println("Job ended: " + endTime);
			System.out.println("The job took " + (endTime.getTime() - startTime.getTime()) / 1000 + " seconds.");
		} else {
			System.out.println("Job failed.");
		}
	}
}
