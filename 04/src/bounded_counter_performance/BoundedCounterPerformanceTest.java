package bounded_counter_performance;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoundedCounterPerformanceTest {

	@Test
	public void test() {
		int numThreads = 10;
		int incsPerThread = 100000;
		BoundedCounter shared = new BoundedCounter(numThreads*incsPerThread);
		Thread[] t = new Thread[numThreads];
		int numRuns = 1;
		for (int i = 0; i < numRuns; i++) {
			shared.reset();
			for (int j = 0; j < numThreads; j++) {
				t[j] = new BoundedCounterIncThread (Integer.toString(j), incsPerThread, shared);
			}
			long start = System.nanoTime();
			for (int j = 0; j < numThreads; j++) {
				t[j].start();
			}
			for (int j=0; j < numThreads; j++) {
				try {
					t[j].join();
				}
				catch (InterruptedException e) { }
			}
			long end = System.nanoTime();
			System.out.printf ("Number of increments:  %,d; Counter value = %,d%n", numThreads*incsPerThread, shared.current());
			// Following prints time in microseconds
			System.out.printf ("Time = %,10.1f microsec%n", (((double) end) - ((double) start)) / 1000);
			assertTrue("foo", (shared.current()) == numThreads*incsPerThread);
		}
	}

}
