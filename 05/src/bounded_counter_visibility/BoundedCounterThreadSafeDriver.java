package bounded_counter_visibility;

public class BoundedCounterThreadSafeDriver {

	public static void main(String[] args) throws InterruptedException {

		  BoundedCounterThreadSafe c = new 
			BoundedCounterThreadSafe (2);

		  Thread t1 = new Thread (new BoundedCounterThreadSafeIncRunnable (c));
		  Thread t2 = new Thread (new BoundedCounterThreadSafeIncRunnable (c));

		  t1.start();
		  t2.start();
		  t1.join();
		  t2.join();

		  System.out.println (c.current());
		}


}
