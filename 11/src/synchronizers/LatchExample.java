package synchronizers;
import java.util.concurrent.CountDownLatch;

/**
 * Based on example from JCIP p. 96
 
 *
 */
public class LatchExample {

	// Thread race!
	public static void main(String[] args) {
		
		// On your marks
		final int numThreads = 10;
		final int numIterations = 1000;  // 1 million
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(numThreads);

		// Get set
		
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread() {
				public void run () {
					try { startGate.await(); }
					catch (InterruptedException e) {}
					for (int j = 0; j < numIterations; j++) {}
					System.out.println ("Thread " + getName() + " finishes.");
					endGate.countDown();
				}
			};
			t.start();
		}

		// Go!

		System.out.println ("And they're off! \n");
//		try {
//			Thread.sleep(500);
//		}
//		catch (InterruptedException e) { }
		long start = System.nanoTime();
		
		startGate.countDown();//minus 1
		
		// Wait for all to finish
		try { endGate.await(); }
		catch (InterruptedException e) {}
		long end = System.nanoTime();
		
		System.out.printf ("\nThe whole race took %,d ns.", end-start);

	}

}
