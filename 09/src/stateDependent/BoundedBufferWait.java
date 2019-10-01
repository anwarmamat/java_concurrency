package stateDependent;
import java.util.ArrayList;

/**
 * Class of bounded buffers implemented using wait() / notifyAll()
 */
public class BoundedBufferWait {

	// Invariant 1:  number of elements is <= maxSize
	// Invariant 2:  elements taken in order they were put

	private final int maxSize;
	private ArrayList<Object> elements;

	BoundedBufferWait (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}

	/**
	 * Wait until there is room, then add an element.
	 * 
	 * Precondition:  number of elements is below maxSize
	 * Postcondition:  elt is added to end of list of elements, waiting threads notified
	 * Exception:  If number of elements is too high, wait until precondition is true.
	 * 
	 * @param elt	Element to add
	 * @throws InterruptedException  Exception thrown if thread interrupted during wait
	 */
	public synchronized void put (Object elt) throws InterruptedException {
		while (elements.size() == maxSize) {
			wait();
		}
		elements.add(elt);
		notifyAll();
	}

	/**
	 * Wait until there is an element in the buffer, then remove at return the first one
	 * 
	 * Precondition:  there is at least one element in list
	 * Postcondition:  first element is removed, returned, waiting threads notified
	 * Exception:  If there are no elements in the list, suspend
	 * 
	 * @return	First element in buffer
	 * @throws InterruptedException	Exception thrown if thread interrupted while waiting
	 */
	public synchronized Object take () throws InterruptedException {
		while (elements.size() == 0)
			wait();
		Object elt = elements.get(0);
		elements.remove(0);
		notifyAll();
		return elt;
	}
}
