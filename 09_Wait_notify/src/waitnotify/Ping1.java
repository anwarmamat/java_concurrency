package waitnotify;

import java.util.concurrent.locks.ReentrantLock;

public class Ping1 extends Thread{
	private Object lock;
	public Ping1(Object lock, String name) {
		super(name);
		this.lock = lock;
		
	}
	@Override 
	public void run() {
//		try {
//			sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		synchronized(lock) {
			System.out.println(currentThread().getName());
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(currentThread().getName());
		}
	}

}
