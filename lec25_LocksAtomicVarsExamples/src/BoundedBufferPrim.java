import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BoundedBufferPrim {
	final static CountDownLatch start = new CountDownLatch(1);
	final Object[] items = new Object[100];
	int putptr, takeptr, count;

	public synchronized void put(Object x) throws InterruptedException {
		while (count == items.length)
			wait();
		items[putptr] = x;
		if (++putptr == items.length)
			putptr = 0;
		++count;
		notifyAll();
	}

	public synchronized Object take() throws InterruptedException {
		while (count == 0)
			wait();
		Object x = items[takeptr];
		if (++takeptr == items.length)
			takeptr = 0;
		--count;
		notifyAll();
		return x;
	}

	public static void main(String[] args) throws InterruptedException {
		final int numWorkers = 5; 
		final BoundedBufferPrim c = new BoundedBufferPrim();
		final ExecutorService executor = Executors.newFixedThreadPool(2*numWorkers);
		for (int i = 0; i < numWorkers; i++) {
			executor.execute(new Producer(c));
		}
		for (int i = 0; i < numWorkers; i++) {
			executor.execute(new Consumer(c));
		}

		start.countDown();
		Thread.sleep(500);
		executor.shutdownNow();

	}

	static class Consumer implements Runnable {
		private final BoundedBufferPrim buffer;

		public Consumer(BoundedBufferPrim c) {
			buffer = c;
		}

		public void run() {
			int i = 0;
			try {
				start.await();
				while (!Thread.interrupted()) {
					++i;
					buffer.take();
				}
			} catch (InterruptedException e1) {
			} finally {
			System.out.println(this + ":" + i);
			}
		}
	}

	static class Producer implements Runnable {
		private final BoundedBufferPrim buffer;

		public Producer(BoundedBufferPrim c) {
			buffer = c;
		}

		public void run() {
			int i = 0;
			try {
				start.await();
				while (!Thread.interrupted()) {
					buffer.put(++i);
				}
			} catch (InterruptedException e1) {
			} finally {
			System.out.println(this + ":" + i);
			}
		}
	}
}
