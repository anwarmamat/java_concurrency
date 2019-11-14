package transactions;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;


public class Driver {
	public static void main(String[] args) {
		
		Configuration conf = new Configuration();
		Job job_conf = new Job(conf);
		//job.setJobName("WordCount");
		
		
		//JobClient my_client = new JobClient();
		// Create a configuration object for the job
		//JobConf job_conf = new JobConf(SalesCountryDriver.class);

		// Set a name of the Job
		job_conf.setJobName("SalePerCountry");

		// Specify data type of output key and value
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(IntWritable.class);

		// Specify names of Mapper and Reducer Class
		job_conf.setMapperClass(SalesMapper.class);
		job_conf.setReducerClass(SalesCountryReducer.class);

		// Specify formats of the data type of Input and output
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);

		// Set input and output directories using command line arguments, 
		//arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.
		
		FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));

		my_client.setConf(job_conf);
		try {
			// Run the job 
			JobClient.runJob(job_conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

