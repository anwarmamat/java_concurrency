import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBufferCond {
	final static CountDownLatch start = new CountDownLatch(1);
	Object[] items = new Object[100];
	int putptr, takeptr, count;
	
	Lock lock = new ReentrantLock();
	Condition notFull = lock.newCondition();
	Condition notEmpty = lock.newCondition();
	

	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length)
				notFull.await();
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0)
				notEmpty.await();
			Object x = items[takeptr];
			if (++takeptr == items.length)
				takeptr = 0;
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final int numWorkers = 5; 
		final BoundedBufferCond c = new BoundedBufferCond();
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
		private final BoundedBufferCond buffer;
	
		public Consumer(BoundedBufferCond c) {
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
		private final BoundedBufferCond buffer;

		public Producer(BoundedBufferCond c) {
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
