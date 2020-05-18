package findbugs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker implements Runnable{
	
	private Long myNtfSeqNbrCounter = Long.valueOf(0);
	
	
	private Lock lock;
	private Condition cond;
	private int n = 0;
	Worker(Lock lock){
		this.lock = lock;
		cond  = lock.newCondition(); 
	}
	
	private Long getNotificationSequenceNumber() {
	     Long result = null;
	     synchronized(myNtfSeqNbrCounter) {
	         result = Long.valueOf(myNtfSeqNbrCounter.longValue() + 1);
	         myNtfSeqNbrCounter = Long.valueOf(result.longValue());
	     }
	     return result;
	 }
	
	@Override
	public void run() {
		
		lock.notifyAll();
		
		synchronized(this) {
			
			
		}
		
		lock.lock();
		System.out.println("locked");
		try {
			
			cond.await();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(n > 10) {
			lock.unlock();
		}
		
	}
	
}

public class LockBug {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Worker w = new Worker(lock);
		Thread t = new Thread(w);
		t.start();

	}

}
