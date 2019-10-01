package bounded_counter_visibility;

public class BoundedCounterThreadSafeIncRunnable implements Runnable {
	private BoundedCounterThreadSafe counter;
	
	BoundedCounterThreadSafeIncRunnable (BoundedCounterThreadSafe counter) {
		this.counter = counter;
	}
	
	public void run () {
			counter.inc();
	}

}
