import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWDictionaryRWL {
	private final Map<Integer, Integer> m = new TreeMap<Integer, Integer>();
	
	// not using fairness policy
	//private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(false);

	// using fairness policy 
	 private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
	private final Lock r = rwl.readLock();
	private final Lock w = rwl.writeLock();

	public Integer get(Integer key) {
		r.lock();
		try {
			return m.get(key);
		} finally {
			r.unlock();
		}
	}

	public Integer put(Integer key, Integer value) {
		w.lock();
		try {
			return m.put(key, value);
		} finally {
			w.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final int numWorkers = 20;
		final RWDictionaryRWL rwd = new RWDictionaryRWL();
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(numWorkers+1);

		// Reader 
		Runnable r = new Runnable() {
			public void run() {
				Random r = new Random();
				try {
					start.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 1000; i++) {
					// System.out.println(rwd.get(r.nextInt(numWorkers - 1)));
					rwd.get(r.nextInt(numWorkers - 1));
				}
				end.countDown();
			}
		};

		// Writer
		Runnable w = new Runnable() {
			public void run() {
				Random r = new Random();
				try {
					start.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++) {
					rwd.put(r.nextInt(numWorkers - 1), r
							.nextInt(numWorkers - 1));
				}
				end.countDown();
			}
		};

		ExecutorService executor = Executors.newFixedThreadPool(numWorkers + 1);
		executor.execute(w);
		for (int i = 0; i < numWorkers; i++) {
			executor.execute(r);
		}
		executor.shutdown();
		long startTime = System.currentTimeMillis();
		start.countDown();
		end.await();
		System.out.println("Elapsed Time:"
				+ (System.currentTimeMillis() - startTime));
	}
}
