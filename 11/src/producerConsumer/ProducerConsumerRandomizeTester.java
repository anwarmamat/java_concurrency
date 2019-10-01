package producerConsumer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Producer-consumer application to test random-number generator
 * Producers generates random integers in range [-n ... n]
 * Consumers retrieve elements, periodically printing sum of numbers so far.
 *
 */
public class ProducerConsumerRandomizeTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<Integer> workQueue = new ArrayBlockingQueue<Integer>(10);
		int numProducers = 100;
		int numConsumers = 20;
		
		for (int i=0; i < numConsumers; i++) {
			new ConsumerThread(workQueue).start();
		}
		
		for (int i=0; i < numProducers; i++) {
			new ProducerThread(workQueue).start();
		}

	}

}
