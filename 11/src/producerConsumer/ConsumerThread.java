package producerConsumer;
import java.util.concurrent.BlockingQueue;

/**
 * Consumer threads for producer-consumer random-number testing
 * Each thread takes an element from the queue and adds it to a running sum.
 * Every so often (snapshot) it prints its sum.
 * 
 *
 */
public class ConsumerThread extends Thread{
	private final int snapshot = 100000;
	private int sum = 0;
	private final BlockingQueue<Integer> queue;
	
	public ConsumerThread (BlockingQueue<Integer> queue) {
		this.queue = queue;
	}
	
	private Integer dequeue() {
		try {
			return(queue.take());
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException ("Interrupted Consumer");
		}
	}
	
	public void run() {
		for (;;) {
			for (int i=0; i < snapshot; i++) {
				sum += dequeue();
			}
			System.out.println("Consumer " + getName() + " sum = " + sum);
		}
	}
}
