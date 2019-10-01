package producerConsumer;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Simple tests of some blocking-queue operations.
 *
 */
public class QueueTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Queue<Integer> q = new ArrayBlockingQueue<Integer>(2);
		q.add(1);
		System.out.println(q.offer(2));
		System.out.println(q);
	}

}
