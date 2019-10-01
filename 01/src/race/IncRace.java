//package race;

/**
 * Program to show data race.  Repeated execution should eventually show t1 and t2
 * assigning "1" to the shared field, even though both perform and increment and the
 * field is initially 0.
 */
public class IncRace {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread (new IncThread ("t1"));
		Thread t2 = new Thread (new IncThread ("t2"));
		t1.start ();
		Thread.sleep (5);
		//t1.join();
		t2.start ();
	}

}
