package deadlock;

/**
 * Class of Runnables that acquires two locks, announced success, then releases locks.
 *
 */
public class TwoLock implements Runnable {
	
	private Object firstLock;
	private Object secondLock;
	private String name;
	
	public TwoLock (Object a, Object b, String name) {
		firstLock = a;
		secondLock = b;
		this.name = name;
	}
	
	public void run () {
		System.out.println (name + " starts.");
		synchronized (firstLock) {
			try {
				Thread.sleep (10);
				} catch (InterruptedException e) {}
			synchronized (secondLock) {
				System.out.println (name + " succeeds.");
			}
		}
	}
}
