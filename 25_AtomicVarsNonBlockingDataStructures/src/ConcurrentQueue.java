import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentQueue<E> {
	private static Long total = 0L;
	private static class Node<E> {
		final E item;
		final AtomicReference<Node<E>> next;

		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = new AtomicReference<Node<E>>(next);
		}
	}

	private final Node<E> dummy = new Node<E>(null, null);
	private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(
			dummy);
	private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(
			dummy);

	public boolean put(E item) {
		Node<E> newNode = new Node<E>(item, null);
		while (true) {
			Node<E> curTail = tail.get();
			Node<E> tailNext = curTail.next.get();
			if (curTail == tail.get()) { 
				if (tailNext != null) {
					tail.compareAndSet(curTail, tailNext);
				} else {
					if (curTail.next.compareAndSet(null, newNode)) {
						tail.compareAndSet(curTail, newNode);
						return true;
					}
				}
			}
		}
	}

	public E take() {
		for (;;) { 
			Node<E> oldHead = head.get(); 
			Node<E> oldTail = tail.get(); 
			Node<E> oldHeadNext = oldHead.next.get(); 
			if (oldHead == head.get()) { 
				if (oldHead == oldTail) {
					if (oldHeadNext == null) {
						return null; 
					}
					tail.compareAndSet(oldTail, oldHeadNext);
				} else { 
					if (head.compareAndSet(oldHead, oldHeadNext)) {
						return oldHeadNext.item;
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final ConcurrentQueue<Integer> cs = new ConcurrentQueue<Integer>();
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
