package transactions;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;

public class SalesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
						
	private final static IntWritable one = new IntWritable(1);

	public void map(LongWritable key, Text value, Context context) 
				throws IOException, InterruptedException {
		String valueString = value.toString();
		System.out.println(valueString);
		String[] SingleCountryData = valueString.split(",");
		context.write(new Text(SingleCountryData[7]), one);
	}
}
