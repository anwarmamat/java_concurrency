package intArraySortUtils;

/**
 * Class of basic countdown latches, with countDown() method overloaded to
 * permit decrements by values other than 1.
 * 
 */
public class BasicCountingLatch {

	int count;	// Invariant:  count >= 0

	public BasicCountingLatch(int count) {
		this.count = count;
	}

	/**
	 * Returns current count value
	 * 
	 * @return Current count value
	 */
	public synchronized int getCount() {
		return count;
	}

	/**
	 * Decrement counter if it is > 0; if it becomes 0, notify waiting threads.
	 */
	public synchronized void countDown() {
		if (count > 0) {
			count--;
			if (count == 0)
				notifyAll(); // Notify waiting threads
		}
	}

	/**
	 * Decrement counter by delta if it is >= delta; if doing so makes counter
	 * 0, notify waiting threads.
	 */
	public synchronized void countDown(int delta) {
		if (count >= delta) {
			count -= delta;
			if (count == 0)
				notifyAll(); // Notify waiting threads
		}
	}

	/**
	 * Increment counter if it is > 0.
	 */
	public synchronized void countUp() {
		if (count > 0)
			count++;
	}

	/**
	 * Increment counter by delta if counter is > 0.
	 * 
	 * Precondition:  delta is >= 0 (needed to ensure counter remains >= 0).
	 * 
	 * @param delta	Amount to increment counter by.
	 */
	public synchronized void countUp(int delta) {
		if (count > 0)
			count += delta;
	}

	/**
	 * Method forces wait until count is <= 0.
	 */
	public synchronized void await() {
		if (count > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
}
