package synchronizers;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Illustration of use of explicit locks, conditions
 * Adapted from http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/locks/Condition.html

 *
 */
public class ArrayBoundedBuffer {
	private final ArrayList<Object> items;
	private final int capacity;
	
	private final Lock lock = new ReentrantLock();
	private final Condition notFull  = lock.newCondition(); // Waiting for not full
	private final Condition notEmpty = lock.newCondition(); // Waiting for not empty

	public ArrayBoundedBuffer (int capacity){
		this.capacity = capacity;
		this.items = new ArrayList<Object>();
	}

	/**
	 * Add element to end of buffer.  Note use of notFull.await() and
	 * notEmpty.signal().
	 * 
	 * @param x		Element to insert
	 * @throws InterruptedException
	 */
	public void put(Object x) throws InterruptedException {
		lock.lock();
		try {
			while (items.size() == capacity) 
				notFull.await();
			items.add(x); 
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Remove first element from buffer.  Note use of notEmpty.await() and
	 * notFull.signal().
	 * 
	 * @return	Removed element
	 * @throws InterruptedException
	 */
	public Object take() throws InterruptedException {
		lock.lock();
		try {
			while (items.size() == 0) 
				notEmpty.await();
			Object x = items.get(0); 
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	} 

}
