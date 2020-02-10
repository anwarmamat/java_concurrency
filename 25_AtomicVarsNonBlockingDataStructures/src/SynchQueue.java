import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchQueue<E> {
	private static Long total = 0L;

	private static class Node<E> {
		final E item;
		Node<E> next;

		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = next;
		}
	}

	private final Node<E> dummy = new Node<E>(null, null);
	private final Node<E> head = dummy;
	private final Node<E> tail = dummy;

	public synchronized boolean put(E item) {
		Node<E> newNode = new Node<E>(item, null);
		tail.next = newNode;
		return true;
	}

	public synchronized E take() {
		E retval;
		if (head.next == null) { // Is queue empty?
			return null; // # Queue is empty, couldn�t dequeue
		} else {
			retval = head.next.item;
			head.next = head.next.next;
		}
		return retval;
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchQueue<Integer> cs = new SynchQueue<Integer>();
		final ExecutorService executor = Executors.newCachedThreadPool();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final int size = 4;
		final CountDownLatch stopSignal = new CountDownLatch(size);

		
		// put threads 
		
		for (int i = 0; i < size / 2; i++) {
			executor.execute(new Runnable() {
				public void run() {
					int j = 0;
					try {
						startSignal.await();
					} catch (InterruptedException e) {
					}
					while (!Thread.interrupted()) {
						cs.put(j);
						//System.out.println("in:" + j);
						++j;
					}
					synchronized (total) {
						total += j;
					}
					stopSignal.countDown();					
				}
			});
		}
		
		// take threads 
		
		for (int i = 0; i < size / 2; i++) {
			executor.execute(new Runnable() {
				public void run() {
					int j = 0;
					try {
						startSignal.await();
					} catch (InterruptedException e) {
					}
					while (!Thread.interrupted()) {
						//System.out.println("out:" + cs.take());
						cs.take();
						++j;
					}
					synchronized (total) {
						total += j;
					}
					stopSignal.countDown();					
				}
			});
		}

		startSignal.countDown();
		Thread.sleep(1000);
		executor.shutdownNow();
		stopSignal.await();
		System.out.println(total);
	}
}
