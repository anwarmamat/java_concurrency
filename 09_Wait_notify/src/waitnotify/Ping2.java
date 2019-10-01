package waitnotify;

import java.util.concurrent.locks.ReentrantLock;

public class Ping2 extends Thread{
	private Object lock;
	public Ping2(Object lock) {
		this.lock = lock;
	}
	@Override 
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		synchronized(lock) {
			System.out.println("ping");
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("ping");
		}
	}

}
