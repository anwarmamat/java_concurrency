package wordCount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Class implementing Map part of word-counting MapReduce application. The four
 * parameter types to Mapper correspond to the input-table key / value and
 * output-table key / value types, respectively.
 * 
 *
 */
public class MapClass extends Mapper<LongWritable, Text, Text, IntWritable> {

	// In intermediate table created by map, assign each key (word) one.
	private final static IntWritable one = new IntWritable(1);

	// Used to hold keys for intermediate table
	private Text word = new Text();

	/*
	 * (non-Javadoc) Function converting key / value pair into intermediate key
	 * / value pairs. The latter are stored in the given context. The key /
	 * value types come from the specification of the input file format
	 * (TextInputFormat) given in WordCount.java. In this format files have
	 * individual lines as the values (hence Text is the value type), with keys
	 * being the position of the first character in the line within the file
	 * (hence LongWritable is the key type).
	 * 
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN,
	 * org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	public void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException {

		// Convert Text value to string, and create tokenizer.
		String line = value.toString();
		// System.out.println("First key: " + key);
		// System.out.println("First line: " + line);
		
		//StringTokenizer tokenizer = new StringTokenizer(line);
		
		String[] tokenizer = line.split("\\P{L}+");
		
		// Insert each token, together with count value of 1, into context.
		
//		while (tokenizer.hasMoreTokens()) {
//			word.set(tokenizer.nextToken());
//			context.write(word, one);
//		}
		for(String s: tokenizer) {
			word.set(s);
			context.write(word, one);
		}
	}

}
