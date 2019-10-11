package visibility;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class to show visibility problem with JCIP example on p. 34.
 *
 */

public class NoVisibilityTest {
	
	private static boolean ready;
	private static int number;
	
	/**
	 * Thread class that reads ready, then asserts that number has the value 42.
	 * 
	 *
	 */
	private static class ReaderThread extends Thread {
		public void run () {
			while (!ready) Thread.yield ();
			assertTrue("", number == 42);
		}
	}
	
	
	
	/**
	 * Test case.  The test creates numThreadsPerTrial number of threads and runs them.
	 */
	@Test
	public void test() throws InterruptedException {
		int numTrials = 10000;
		int numThreadsPerTrial = 10;
		ReaderThread[] t = new ReaderThread[numThreadsPerTrial];
		for (int i = 0; i < numTrials; i++) {
			ready = false;
			number = 0;
			for (int j = 0; j < numThreadsPerTrial; j++) {
				t[j] = new ReaderThread();
			}
			for (int j = 0; j < numThreadsPerTrial; j++) {
				t[j].start();
			}
			number = 42;
			ready = true;
			for (int j = 0; j < numThreadsPerTrial; j++) {
				t[j].join();
			}
		}
	}

}
