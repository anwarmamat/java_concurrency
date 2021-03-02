/**
 * The inc() acquires the lock, and calls isMaxed, which tries to acquire the 
 * same lock. Because the lock is reentrant, the same thread can acquire the same
 * lock it is holding. This will not create deadlock.
 */
package mutex;


public class Counter {
	private int count;
	private Mutex lock = new Mutex();
	
	
	public void inc() {
		lock.lock();   
				count++;
		lock.unlock();  
	}
	
	public  int get(){
		return count;
	}

}
