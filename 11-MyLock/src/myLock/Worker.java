package myLock;

public class Worker extends Thread{
	private MyLock lock;
	public Worker(MyLock lock) {
		this.lock = lock;
	}
	public void run() {
		try {
			
			lock.lock();
			Log.msg(this, "acquired lock");
			/*
			 *  if the threads is holding the lock exclusively
			 */
			if(lock.isHeldExclusively()) {
				Log.msg(this, "holds the Lock exclusively");
			}else {
				Log.msg(this, "holds the Lock NOT exclusively");
			}
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		
		
	}
}
