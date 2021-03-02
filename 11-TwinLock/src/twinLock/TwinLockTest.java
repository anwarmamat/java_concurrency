/**
 * Allow two lockst at the same time
 * 
 */
package twinLock;

import java.util.concurrent.locks.Lock;

public class TwinLockTest {
	public static void main(String[] args) {
		final Lock lock = new TwinLock();
		class Worker extends Thread {
			@Override
			public void run() {
				while (true) {
					lock.lock();
					try {
						int r = (int)(Math.random() * 100);
						Thread.sleep(r);
						System.out.println("Current thread:" + Thread.currentThread().getName());
						Thread.sleep(100L);
					} catch (Exception ex) {
					} finally {
						lock.unlock();
					}
				}
			}
		}
		for (int i = 0; i < 10; i++) {
			Worker w = new Worker();
			w.start();
		}
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						int r = (int)(Math.random() * 200);
						Thread.sleep(r);
						System.out.println();
					} catch (Exception ex) {
					}
				}
			}
		}.start();
		try {
			Thread.sleep(20000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}