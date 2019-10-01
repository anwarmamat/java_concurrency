package threadsafety;
/**
 *  When new Runnables are created for each thread, then 
 *  instance variables of the Runable are not hared, but the 
 *  static variables are still shared.
 *
 */
public class MultipleRunnable {

	public static void main(String[] args) {
		RunnableShare r1 = new RunnableShare();
		RunnableShare r2 = new RunnableShare();
		Thread t1 = new Thread(r1,"T1");
		Thread t2 = new Thread(r2,"T2");
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Count = " + r1.getCount());
		System.out.println("Count = " + r2.getCount());
		
		System.out.println("Copies = " + r1.getCopies());
		System.out.println("Copies = " + r2.getCopies());
	}

}
