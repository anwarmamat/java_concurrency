import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPseudoRandom {
  private static Long total = 0L;

  // lock configured in non-fair mode 
  private final Lock lock = new ReentrantLock(false);  
  private int seed;  
  
  ReentrantLockPseudoRandom(int seed) {this.seed = seed;}  
  public int nextInt(int n) {    
    lock.lock();   
    try {    
      int s = seed;  seed = calculateNext(s);  int remainder = s % n;    
      return remainder > 0 ? remainder : remainder + n; 
   } finally { lock.unlock();}  
  }
  
  // return something simple for demonstration purposes
  private int  calculateNext (int s) {
	  return s+1;
  }
  
  public static void main (String [] args) throws InterruptedException {
	  final ReentrantLockPseudoRandom pr = new ReentrantLockPseudoRandom(0);
	  final ExecutorService executor = Executors.newCachedThreadPool();
	  final CountDownLatch startSignal = new CountDownLatch(1);
	  final int size = 5;
	  final CountDownLatch stopSignal = new CountDownLatch(size);


		for (int i = 0; i < size; i++) {
			executor.execute(new Runnable() {
				public void run() {
					int j = 0;
					try {
						startSignal.await();
					} catch (InterruptedException e) {
					}
					while (!Thread.interrupted()) {
						pr.nextInt(100);
						++j;
					}
					synchronized (total) {
						total += j;
						stopSignal.countDown();
					}
				}
			});
		}
		startSignal.countDown();
		Thread.sleep(500);
		executor.shutdownNow();
		stopSignal.await();
		System.out.println(total);
  }
}