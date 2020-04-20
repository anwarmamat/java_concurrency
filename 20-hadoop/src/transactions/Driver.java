package transactions;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class Driver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		Job job_conf = new Job(conf);
		job_conf.setJobName("Sales");
		
	
		// Set a name of the Job
		job_conf.setJobName("SalePerCountry");

		// Specify data type of output key and value
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(IntWritable.class);

		// Specify names of Mapper and Reducer Class
		job_conf.setMapperClass(SalesMapper.class);
		job_conf.setReducerClass(SalesCountryReducer.class);

		// Specify formats of the data type of Input and output
		job_conf.setInputFormatClass(TextInputFormat.class);
		job_conf.setOutputFormatClass(TextOutputFormat.class);

		// Set input and output directories using command line arguments, 
		//arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.
		
		
		TextInputFormat.setInputPaths(job_conf, new Path("inputs"));
		TextOutputFormat.setOutputPath(job_conf, new Path("outputs"));
		
		Date startTime = new Date();
		System.out.println("Job started: " + startTime);
		boolean success = job_conf.waitForCompletion(true);
		if (success) {
			Date endTime = new Date();
			System.out.println("Job ended: " + endTime);
			System.out.println("The job took " + (endTime.getTime() - startTime.getTime()) / 1000 + " seconds.");
		} else {
			System.out.println("Job failed.");
		}
		
	}
}

