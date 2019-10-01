package stateDependent;
import java.util.ArrayList;

public class BoundedBufferWaitLockParam {
	
	final int maxSize;
	final ArrayList<Object> elements;
	final Object syncLock;
	
	BoundedBufferWaitLockParam (Object lock, int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
		syncLock = lock;
	}
	
	BoundedBufferWaitLockParam (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
		syncLock = this;
	}

	// Pre:  number of elements is below maxSize
	// Post:  elt is added to end of list of elements, waiting threads notified
	// Exception:  If number of elements is too high, suspend.
	public void put (Object elt) throws InterruptedException {
		synchronized (syncLock) {
			while (elements.size() == maxSize) syncLock.wait();
			elements.add(elt);
			syncLock.notifyAll();
		}
	}

	// Pre:  there is at least one element in list
	// Post:  first element is removed, returned, waiting threads notified
	// Exception:  If there are no elements in the list, suspend
	public Object take () throws InterruptedException {
		synchronized (syncLock) {
			while (elements.size() == 0)
				syncLock.wait();
			Object elt = elements.get(0);
			elements.remove(0);
			syncLock.notifyAll();
			return elt;
		}
	}
}
