import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterTest {
	final static Counter uc = new UnSynchronizedCounter();
	final static Counter sc = new SynchronizedCounter();
	final static Counter ac = new AtomicCounter();
	final static int numThreads = 10;
	
	
	static Counter aCounter;
	static CountDownLatch start; 
	static CountDownLatch end;

	public static void main(String[] args) throws InterruptedException {
		Runnable cr = new Runnable() {
			public void run() {
				try {
					start.await();
				} catch (InterruptedException e) {
				}
				for (int i = 0; i < 100000; i++) {
					aCounter.increment();
				}
				end.countDown();
			}
		};

		start = new CountDownLatch(1);
		end = new CountDownLatch(numThreads);
		
		aCounter = uc;

		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		for (int j = 0; j < numThreads; j++) {
			executor.execute(cr);
		}

		System.out.println("Incrementing the unsynchronized counter");
		long sTime = System.currentTimeMillis();
		start.countDown();
		executor.shutdown();
		end.await();
		System.out.println("Final Value:" + aCounter.value());
		System.out.println("Total time:" + (System.currentTimeMillis() - sTime));
		System.out.println();
		
		start = new CountDownLatch(1);
		end = new CountDownLatch(numThreads);
		aCounter = sc;

		executor = Executors.newFixedThreadPool(numThreads);
		for (int j = 0; j < numThreads; j++) {
			executor.execute(cr);
		}

		System.out.println("Incrementing the synchronized counter");
		sTime = System.currentTimeMillis();
		start.countDown();
		executor.shutdown();
		end.await();
		System.out.println("Final Value:" + aCounter.value());
		System.out
				.println("Total time:" + (System.currentTimeMillis() - sTime));
		System.out.println();
		
		start = new CountDownLatch(1);
		end = new CountDownLatch(numThreads);
		aCounter = ac;
		System.out.println("Incrementing the atomic counter");
		executor = Executors.newFixedThreadPool(numThreads);
		for (int j = 0; j < numThreads; j++) {
			executor.execute(cr);
		}

		sTime = System.currentTimeMillis();
		start.countDown();
		executor.shutdown();
		end.await();
		System.out.println("Final Value:" + aCounter.value());
		System.out
				.println("Total time:" + (System.currentTimeMillis() - sTime));
	}
}
