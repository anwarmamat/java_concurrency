package parallelSum;

import java.text.DecimalFormat;

public class SumDriverCallableTaskCompletion {

	static public String formatNanosAsMillis (long ns) {
	      DecimalFormat myFormatter = new DecimalFormat("###,###.##");
	      double millis = (double)ns / 1000000.0;
	      String output = myFormatter.format(millis);
	      return (output);
	   }

	public static void main(String[] args) {
		int n = 200000000;  // 200 million
		int b = 25;
		int nWorkers = 20;  // number of worker threads in executor
		int nTasks = 200;	// number tasks to break sum computation into
		Integer a[] = RandomIntArray.getRandomIntArray(n, b);

		long start = System.nanoTime();
		int seq = SequentialIntArraySum.sum (a);
		long end = System.nanoTime();

		System.out.format ("Seq. sum = %,d; time = %s milliseconds\n", seq, formatNanosAsMillis (end-start));

		ParallelIntArraySumCallableCompletion ps = new ParallelIntArraySumCallableCompletion(nWorkers,nTasks);
		start = System.nanoTime();
		int par = ps.sum(a);
		end = System.nanoTime();

		System.out.format ("Par. sum = %,d, time = %s milliseconds\n", par, formatNanosAsMillis (end-start));


	}
}
