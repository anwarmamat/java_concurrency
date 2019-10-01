package threads;

/**
 * Test program for isAlive(), join(), daemon status
 */
public class TerminateTester {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread (new SleepTerminateRunnable ());
		Thread u = new Thread() {
			public void run() {
				new SleepTerminateRunnable().run();
			}
		};
//		System.out.println ("alive value for t is:  " + t.isAlive ());
		//t.setDaemon (true);
//		t.start ();
//		t.join();
//		u.start ();
		//t.setDaemon (true);
		System.out.println ("alive value for t is:  " + t.isAlive ());	
		//t.interrupt();
		//t.join();
//		t.setDaemon(true);
//		t.run(); // This is a no-op because t's "target" field is nulled out after t.join().
		//u.join();
		u.run(); // This is not a no-op:  run() method is still there because u defined using inheritance.
		System.out.println ("Main finishing ...");
	}

}
