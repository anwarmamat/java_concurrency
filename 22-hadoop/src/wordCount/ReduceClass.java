package wordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Implements Reducer class for word-counting. The four type parameters to
 * Reducer denote the input key/value and output key/value types.
 * 
 * @author Rance Cleaveland
 *
 */
public class ReduceClass extends Reducer<Text, IntWritable, Text, IntWritable> {

	/*
	 * (non-Javadoc) Reducer function, which sums the values associated with the
	 * given key.
	 * 
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN,
	 * java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 */

	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws InterruptedException, IOException {
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}
		context.write(key, new IntWritable(sum));
	}
}
