package myLock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;


public class MyLock {
	 private final Sync sync = new Sync();
	 
	 public void unlock() {
	        sync.release(0);
	    }

	    public void lock()
	            throws InterruptedException {
	        sync.acquire(0);
	    }
	    
	    
	    public boolean tryLock(int time, TimeUnit timeUnit)
	            throws InterruptedException {
   	
	        return sync.tryAcquireNanos(0, timeUnit.toNanos(time));
	    }
	    
	    public Collection<Thread> blockedThgreads() {
	    	return sync.getQueuedThreads();
	    }
	    
	    public Thread getFirstQueuedThread() {
	    	
	    	return sync.getFirstQueuedThread();
	    }
	    public boolean isHeldExclusively() {
	    	
	    	return sync.isHeldExclusively();
	    }
	    
	    public boolean isQueued(Thread t) {
	    	return sync.isQueued(t);
	    }
	    
	    
	 
	 private static class Sync extends AbstractQueuedSynchronizer {
		 /* is the lock already held by some thread ? */
	        protected boolean isHeldExclusively() {
	            return getState() == 1;
	        }

	        /*
	         * tryAcquire first checks the lock state. If it is unheld, it tries to
	         * update the lock state to indicate that it is held.
	         */
	        protected boolean tryAcquire(int acquires) {

	            if (compareAndSetState(0, 1)) {
	                setExclusiveOwnerThread(Thread.currentThread());
	                return true;
	            }
	            return false;
	        }

	        /*
	         * tryRelease first checks the lock state. If it is unheld, it throws
	         * and IllegalMonitorStateException else it tries to update the lock
	         * state to indicate that it is unheld.
	         */
	        protected boolean tryRelease(int releases) {

	            if (getState() == 0)
	                throw new IllegalMonitorStateException();
	            setExclusiveOwnerThread(null);
	            setState(0);
	            return true;
	        }

	    }
}	


