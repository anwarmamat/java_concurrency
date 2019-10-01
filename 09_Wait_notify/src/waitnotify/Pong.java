package waitnotify;

import java.util.concurrent.locks.ReentrantLock;

public class Pong extends Thread{
	private Object lock;
	public Pong(Object lock, String name) {
		super(name);
		this.lock = lock;
	}
	@Override 
	public void run() {
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(lock) {
			System.out.println("pong");
			lock.notify();
		}	
	}

}
