package producerConsumer;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Producer thread for random-number testing.
 * Each producer generates numbers in the range (-n .. n) and puts them
 * in the work queue.

 *
 */
public class ProducerThread extends Thread {
	
	private final BlockingQueue<Integer> queue;  // Work queue
	Random r = new Random();	// Random-value generator
	private final int n = 50;
	
	public ProducerThread (BlockingQueue<Integer> queue) {
		this.queue = queue;
	}
	
	private void enqueue (int i) {
		try {
			queue.put(i);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException ("Interrupted Producer");
		}
	}
	
	private int nextInt() {
		return(r.nextInt((2*n)+1) - n);
	}
	
	public void run () {
		
		for (;;) {
			enqueue(nextInt());
		}
	}
}
