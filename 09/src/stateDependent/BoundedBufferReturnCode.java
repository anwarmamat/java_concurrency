package stateDependent;
import java.util.ArrayList;
	
public class BoundedBufferReturnCode {

	// Invariant:  number of elements is <= maxSize
		
	private final int maxSize;
	private ArrayList<Object> elements;

	/**
	 * Class of return values for put(), take()
	 */
	public class ReturnVal {
		public final Object obj;
		public final boolean code;
		ReturnVal (Object obj, boolean code) {
			this.obj = obj;
			this.code = code;
		}
	}
		
	BoundedBufferReturnCode (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}
		
	/**
	 * Insert element into back of buffer
	 * 
	 * Pre:  none
	 * Post:  elt is added to end of list of elements if list size is below maxSize, and true returned; 
	 *        otherwise, false returned
	 * Exception:  none
	 * 
	 * @param elt	Element to insert
	 * @return	Object containing boolean indicating if insertion was successful
	 */
	public synchronized ReturnVal put (Object elt) {
		if (elements.size() < maxSize) {
			elements.add(elt);
			return new ReturnVal(null, true);
		}
		else return new ReturnVal(null, false);
	}

	/**
	 * Remove and return element at front of buffer.
	 * 
	 * Pre:  none
	 * Post:  if list is nonempty, return first element and true; otherwise,
	 *        return null and false
	 * Exception:  none
	 * 
	 * @return
	 */
	public synchronized ReturnVal take () {
		if (elements.size() > 0) {
			Object elt = elements.get(0);
			elements.remove(0);
			return new ReturnVal(elt, true);
		}
		else return new ReturnVal(null, false);
	}
}
