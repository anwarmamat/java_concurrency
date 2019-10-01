package stateDependent;
import java.util.ArrayList;

public class BoundedBufferTimedWait {

	// Invariant:  number of elements is <= maxSize

	private final int maxSize;
	private ArrayList<Object> elements;

	BoundedBufferTimedWait (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}

	// Pre:  number of elements is below maxSize
	// Post:  elt is added to end of list of elements, waiting threads notified
	// Exception:  If number of elements is too high, suspend; if timeout occurs, throw exception
	public synchronized void put (Object elt, long allowedDuration)
			throws Exception, InterruptedException {
		long startTime = System.currentTimeMillis();
		long timeLeft = allowedDuration;
		while (elements.size() == maxSize) {
			wait(timeLeft);
			// Check if buffer has space
			if (elements.size() < maxSize) {
				break;  // Break out of loop
			}
			else {
				// Check if time has expired
				long elapsed = System.currentTimeMillis() - startTime;
				timeLeft = allowedDuration - elapsed;
				if (timeLeft <= 0) throw new Exception ("Timeout");
			}
		}
		elements.add(elt);
		notifyAll();
	}

	// Pre:  there is at least one element in list
	// Post:  first element is removed, returned, waiting threads notified
	// Exception:  If there are no elements in the list, suspend; if timeout occurs, throw Exception
	public synchronized Object take (long allowedDuration)
			throws Exception, InterruptedException {
		long startTime = System.currentTimeMillis();
		long timeLeft = allowedDuration;
		while (elements.size() == 0) {
			wait(timeLeft);
			// Check if buffer has an element; if so, delete and first element
			if (elements.size() > 0) {
				Object elt = elements.get(0);
				elements.remove(0);
				notifyAll();
				return elt;
			}
			else {
				// Check if time has expired
				long elapsed = System.currentTimeMillis() - startTime;
				timeLeft -= elapsed;
				if (timeLeft <= 0) throw new Exception ("Timeout");
			}
		}
		throw new Exception ("Logic error"); // here as a check; should never execute
	}

}
