package synchronizers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sample program illustrating that trying to lock a Lock object leaves thread in WAITING state,
 * rather than BLOCKED state.  The latter is reserved for monitor locks only.
 * 
 *
 */
public class LockTest {
	private static Lock l = new ReentrantLock();
	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run () {
				l.lock();
			}
		};

		l.lock();  //main acquires the lock
		t.start();	//start t
		try {
			Thread.sleep(50);
		}
		catch (InterruptedException e) { };
		System.out.println(t.getState());
	}

}
