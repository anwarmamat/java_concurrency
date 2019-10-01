package deadlock;

public class DeadlockTest {

	public static void main(String[] args) {
		Object lockA = new Object ();
		Object lockB = new Object ();
		
		Thread t1 = new Thread (new TwoLock (lockA, lockB, "AB"));
		Thread t2 = new Thread (new TwoLock (lockB, lockA, "BA"));

		t1.start ();
		t2.start ();
		
		System.out.println ("Main finishes.");
	}

}
