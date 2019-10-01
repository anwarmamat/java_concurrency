package delegation;

import monitorPattern.BoundedCounterThreadSafe;

/**
 * Class of two counters that should always have the same value.  Is this a valid
 * application of delegation?
 *
 */
public class DualCounter {

	// Invariant:  primary and backup should have the same value.
	
	private BoundedCounterThreadSafe primary = new BoundedCounterThreadSafe(Integer.MAX_VALUE);
	private BoundedCounterThreadSafe backup = new BoundedCounterThreadSafe(Integer.MAX_VALUE);

	/**
	 * Reset both counters.
	 */
	public void reset () {
		primary.reset();
		backup.reset();
	}
	
	/**
	 * Increment both counters.
	 */
	public void inc () {
		primary.inc();
		backup.inc();
	}
}
