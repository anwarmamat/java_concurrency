/**
 * A nonrentrant lock implementation 
 * 
 * 
 * Anwar Mamat
 * 
 * 
 */
package myLock;

import java.util.Collection;

public class MyLockDriver {

	public static void main(String[] args) {
		MyLock lock = new MyLock();
		Thread t1 = new Worker(lock);
		Thread t2 = new Worker(lock);
		Thread t3 = new Worker(lock);
		t1.start();
		t2.start();
		t3.start();
		
		//main waits for workers to start
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		/*
		 * 	all the currently blocked threads 
		 */
		Collection<Thread> threads = lock.blockedThgreads();
		for(Thread t: threads) {
			Log.msg(t,"is blocked");
		}
		
		/*
		 * Get the longest waiting thread
		 */
		Log.msg(lock.getFirstQueuedThread(), "is the first thread in queue");

		if(lock.isQueued(lock.getFirstQueuedThread())) {
			Log.msg(lock.getFirstQueuedThread(), "is in Queue");
		}
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
