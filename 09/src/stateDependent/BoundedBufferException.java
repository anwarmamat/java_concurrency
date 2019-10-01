package stateDependent;
import java.util.ArrayList;

public class BoundedBufferException {
	
	// Invariant:  number of elements is <= maxSize
	
	private final int maxSize;
	private ArrayList<Object> elements;
	
	BoundedBufferException (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}

	/**
	 * Inserts element into back of buffer.
	 * 
	 * Pre:  number of elements is below maxSize
	 * Post:  elt is added to end of list of elements
	 * Exception:  If number of elements is too high, throw exception.
	 * 
	 * @param elt	element to insert
	 * @throws Exception	Thrown if buffer is full.
	 */
	public synchronized void put (Object elt) throws Exception {
		if (elements.size() < maxSize) {
			elements.add(elt);
		}
		else throw new Exception("Put error");
	}

	/**
	 * Removes and returns first element in buffer.
	 * 
	 * Pre:  there is at least one element in list
	 * Post:  first element is removed, returned
	 * Exception:  If there are no elements in the list, an exception is thrown
	 * 
	 * @return	First element
	 * @throws Exception	Thrown if buffer is empty
	 */
	public synchronized Object take () throws Exception {
		if (elements.size() > 0) {
			Object elt = elements.get(0);
			elements.remove(0);
			return elt;
		}
		else throw new Exception ("Take error");
	}
}
