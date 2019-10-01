package synchronizers;
import java.util.concurrent.CountDownLatch;

public class LatchExample2 {
	public static void main(String[] args) {
		final int numThreads = 5;
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(numThreads);
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread() {
				public void run () {
					try { startGate.await(); }
					catch (InterruptedException e) {}
					System.out.print("1");
					endGate.countDown();
				}
			};
			t.start();
		}
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) { }
		System.out.print ("0");
		startGate.countDown();
		try { endGate.await(); }
		catch (InterruptedException e) {}
		System.out.println ("2");
	}

}
