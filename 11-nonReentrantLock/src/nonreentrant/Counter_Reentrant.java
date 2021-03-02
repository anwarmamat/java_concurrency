/**
 * The inc() acquires the lock, and calls isMaxed, which tries to acquire the 
 * same lock. Because the lock is reentrant, the same thread can acquire the same
 * lock it is holding. This will not create deadlock.
 */
package nonreentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Counter_Reentrant implements Counter {
	private int count;
	private ReentrantLock lock = new ReentrantLock();
	
	public boolean isMaxed() {	
		boolean r = false;
		lock.lock();
			r =  count > 10000;
		lock.unlock();
	    return r;
	}
	
	public void inc() {
		lock.lock();
			if(!isMaxed()) 
				count++;
	    lock.unlock();
	}
	
	public  int get(){
		return count;
	}

}
