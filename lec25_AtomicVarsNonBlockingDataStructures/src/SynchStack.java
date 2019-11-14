import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchStack<E> {
	private static Long total = 0L;

	private static class Node<E> {
		public final E item;
		public Node<E> next;

		public Node(E item) {
			this.item = item;
		}
	}

	Node<E> top;

	public synchronized void push(E item) {
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead;
		oldHead = top;
		newHead.next = oldHead;
	}

	public synchronized E pop() {
		E oldHead;
		if (top == null)
			return null;
		oldHead = top.item;
		top = top.next;
		return oldHead;
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchStack<Integer> cs = new SynchStack<Integer>();
		final ExecutorService executor = Executors.newCachedThreadPool();
		final CountDownLatch startSignal = new CountDownLatch(1);
		final int size = 4;
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
					}
					for (int i=0; i<N; i++) {
						/*
						try {
							Thread.sleep(r.nextInt(10));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
						cs.push(j);
						++j;
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
					}
					for (int i=0; i<N; i++) {
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
		System.out.println(end-start);
	}
}
