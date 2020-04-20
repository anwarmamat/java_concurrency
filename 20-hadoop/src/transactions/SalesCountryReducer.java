package transactions;

import java.io.IOException;
import java.util.*;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Reducer;

public class SalesCountryReducer extends  Reducer<Text, IntWritable, Text, IntWritable> {

	public void reduce(Text t_key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		Text key = t_key;
		int frequencyForCountry = 0;
		for(IntWritable value:values) {
			frequencyForCountry += value.get();
		}
		context.write(key, new IntWritable(frequencyForCountry));
	}
}
