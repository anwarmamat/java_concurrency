package delegation;

import monitorPattern.BoundedCounterThreadSafe;

// 

/**
 * Thread-safe class that counts the number of 0s processed since last reset.
 * Thread-safety is delegated to the BoundedCounterThreadSafe class, which is thread-safe.
 *
 */
public class ZeroCounter {
	
	// Invariant:  count records the number of 0s processed since the most recent
	// reset, or since the object was created, provided count never exceeds MAX_VALUE.
	
	final private BoundedCounterThreadSafe count = new BoundedCounterThreadSafe (Integer.MAX_VALUE);
	
	/**
	 * Processes an integer input, incrementing count if input is a 0
	 * 
	 * Precondition:  none
	 * Postcondition:  count is incremented if input is 0, and left unchanged otherwise
	 * Exception:  none
	 * 
	 * @param i	Input to process
	 */
	public void processInt (int i) {
		if (i == 0) count.inc();
	}
	
//	/**
//	 * Processes an integer and returns count.  Since method uses two calls to
//	 * count, it must use synchronization; delegation alone does not suffice
//	 * 
//	 * Precondition:  none
//	 * Postcondition:  increment counter if input is 0, and return counter value
//	 * Exception:  none
//	 * 
//	 * @param i	Integer to process
//	 * @return	Value of updated counter
//	 */
//	public int processAndCount (int i) {
//		synchronized (count) {
//			if (i == 0) count.inc();
//			return count.current();
//		}
//	}
	
	/**
	 * Returns current count of number of zeros since last reset
	 * 
	 * Precondition:  none
	 * Postcondition:  returns current value of count
	 * Exception:  none
	 * @return	Value of count
	 */
	public int getCount () {
		return count.current();
	}

	/**
	 * Resets count to 0.
	 * 
	 * Precondition:  none
	 * Postcondition:  resets count to 0
	 * Exception:  none
	 */
	public void reset () {
		count.reset();
	}
}
