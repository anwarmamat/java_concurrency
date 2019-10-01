package threads;

/**
 * Test various thread operations
 */
public class ThreadStateTester {

	private static void printState (Thread t){
		System.out.println ("Thread state of " + t.getName() + " is:  " + t.getState());
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread ();
		printState (t);
		t.start ();
		printState (t);
		t.join ();
		printState(Thread.currentThread());
		//t.join();
		printState (t);
	}

}
