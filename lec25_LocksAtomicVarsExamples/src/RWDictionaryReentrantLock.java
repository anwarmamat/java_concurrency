import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RWDictionaryReentrantLock {
	private final Map<Integer, Integer> m = new TreeMap<Integer, Integer>();
	
	private final ReentrantLock rl = new ReentrantLock();

	
	public Integer get(Integer key) {
		rl.lock();
		try {
			return m.get(key);
		} finally {
			rl.unlock();
		}
	}

	public Integer put(Integer key, Integer value) {
		rl.lock();
		try {
			return m.put(key, value);
		} finally {
			rl.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final int numWorkers = 20;
		final RWDictionaryReentrantLock rwd = new RWDictionaryReentrantLock();
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
					//System.out.println(rwd.get(r.nextInt(numWorkers - 1)));
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
