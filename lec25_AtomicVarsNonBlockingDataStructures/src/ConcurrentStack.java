import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentStack<E> {
	private static Long total = 0L;

	private static class Node<E> {
		public final E item;
		public Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}

	// private AtomicInteger count = new AtomicInteger(0);
	// private AtomicInteger tries = new AtomicInteger(0);
	AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

	public void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		do {
			oldHead = top.get();
			newHead.next = oldHead;
			// tries.incrementAndGet();
		} while (!top.compareAndSet(oldHead, newHead));
		// count.incrementAndGet();
	}

	public E pop() {
		Node<E> oldHead;
		Node<E> newHead;
		do {
			// tries.incrementAndGet();
			oldHead = top.get();
			if (oldHead == null) {
				// count.incrementAndGet();
				return null;
			}
			newHead = oldHead.next;
		} while (!top.compareAndSet(oldHead, newHead));
		// count.incrementAndGet();
		return oldHead.item;
	}

	public static void main(String[] args) throws InterruptedException {
		final ConcurrentStack<Integer> cs = new ConcurrentStack<Integer>();
		final ExecutorService executor = Executors.newCachedThreadPool();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final int size = 2;
		final CountDownLatch stopSignal = new CountDownLatch(size);
		final int N = 1000000;
		
		@SuppressWarnings("unused")
		final Random r = new Random();
		
		// push threads

		for (int i = 0; i < size / 2; i++) {
			executor.execute(new Runnable() {
				public void run() {
					int j = 0;
					try {
						startSignal.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < N; i++) {
						cs.push(j);
						++j;
						/*
						try {
							Thread.sleep(r.nextInt(10));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
					synchronized (total) {
						total += j;
						stopSignal.countDown();
					}
				}
			});
		}

		// pop threads

		for (int i = 0; i < size / 2; i++) {
			executor.execute(new Runnable() {
				public void run() {
					int j = 0;
					try {
						startSignal.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < N; i++) {
						cs.pop();
						++j;
						/*
						try {
							Thread.sleep(r.nextInt(10));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
					synchronized (total) {
						total += j;
						stopSignal.countDown();
					}
				}
			});
		}

		executor.shutdown();
		long start = System.currentTimeMillis();
		startSignal.countDown();
		stopSignal.await();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
