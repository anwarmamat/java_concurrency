import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RWDictionaryIntrinsicLock {
	private final Map<Integer, Integer> m = new TreeMap<Integer, Integer>();

	public synchronized Integer get(Integer key) {
		return m.get(key);
	}

	public synchronized Integer put(Integer key, Integer value) {
		return m.put(key, value);
	}

	public static void main(String[] args) throws InterruptedException {
		final int numWorkers = 20;
		final RWDictionaryIntrinsicLock rwd = new RWDictionaryIntrinsicLock();
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(numWorkers+1);

		// reader
		Runnable r = new Runnable() {
			public void run() {
				Random r = new Random();
				try {
					start.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 1000; i++) {
					//System.out.println(rwd.get(r.nextInt(numWorkers-1)));
					rwd.get(r.nextInt(numWorkers-1));
				}
				end.countDown();
				}
		};

		// writer 
		Runnable w = new Runnable() {
			public void run() {
				Random r = new Random();
				try {
					start.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++) {
					rwd.put(r.nextInt(numWorkers-1), r.nextInt(numWorkers-1));
				}
				end.countDown();
			}
		};

		ExecutorService executor = Executors.newFixedThreadPool(numWorkers+1);
		executor.execute(w);
		for (int i = 0; i < numWorkers; i++) {
			executor.execute(r);
		}
		executor.shutdown();
		long startTime = System.currentTimeMillis();
		start.countDown();
		end.await();
		System.out.println("Elapsed Time:" + (System.currentTimeMillis() - startTime));
	}
}
