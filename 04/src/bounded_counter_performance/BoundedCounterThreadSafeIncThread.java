package bounded_counter_performance;

public class BoundedCounterThreadSafeIncThread extends Thread{
	private BoundedCounterThreadSafe counter;
	private int numIncs;
	
	BoundedCounterThreadSafeIncThread (String name, int numIncs, BoundedCounterThreadSafe counter) {
		this.setName(name);
		this.numIncs = numIncs;
		this.counter = counter;
	}
	
	public void run () {
//		System.out.println ("Thread " + this.getName () + " started.");
		for (int i = 0; i < numIncs; i++) {
			counter.inc();
//			try {Thread.sleep(10);} catch (InterruptedException e) {}
//			System.out.println ("Thread " + this.getName() + ":  " +
//					" counter = " + counter.current());
		}
//		System.out.println ("Thread " + this.getName () + " finished.");
	}

}
