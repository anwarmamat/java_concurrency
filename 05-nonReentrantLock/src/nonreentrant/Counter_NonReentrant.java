/**
 * The inc() acquires the lock, and calls isMaxed, which tries to acquire the 
 * same lock. But the lock is not reentrant, therefore, it creates a deadlock.
 */
package nonreentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Counter_NonReentrant implements Counter {
	private int count;
	private NonReentrantLock lock = new NonReentrantLock();
	
	public boolean isMaxed() {	
		boolean r = false;
		try {
			lock.lock();
			r =  count > 10000;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    		//lock.tryLock(1, TimeUnit.SECONDS); //TimeUnit.MILLISECONDS may fail
			
		lock.unlock();
	    return r;
	}
	
	public void inc() {
		try {
			lock.lock();
			if(!isMaxed()) 
				count++;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    lock.unlock();
	}
	
	public  int get(){
		return count;
	}

}
