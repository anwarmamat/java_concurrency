package lock_performance;

public class LockTimer {
	
	// Converts nanoseconds to microseconds, prints the result
	
	public static void printTime (String descr, long duration){
		System.out.printf ("Execution time " + descr +":  %,10.1f microsec%n", (((double) duration) / 1000));
	}

	// Tests time needed to do operations without, with locking
	
	public static void main(String[] args) {
		
		final int numIterations = 1000000;

		int val = 0;
		long start = System.nanoTime();
		for (int i = 0; i < numIterations; i++) {
			val++;
		}
		long end = System.nanoTime();
		
		long time1 = end-start;
		printTime ("no locking", time1);
		
		val = 0;
		start = System.nanoTime();
		for (int i = 0; i < numIterations; i++) {
			synchronized(LockTimer.class) {
				val++;
			}
		}
		end = System.nanoTime();
		long time2 = end-start;
		printTime ("locking   ", time2);
		
		double ratio = (double)time2 / (double)time1;
		System.out.printf ("Ratio = %10.1f", ratio);
	}

}
