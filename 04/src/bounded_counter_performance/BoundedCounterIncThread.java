package bounded_counter_performance;

public class BoundedCounterIncThread extends Thread {
	
	private BoundedCounter counter;
	private int numIncs;
	
	public BoundedCounterIncThread (String name, int numIncs, BoundedCounter counter) {
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
