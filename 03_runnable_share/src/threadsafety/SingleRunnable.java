package threadsafety;
/**
 * When multiple threads are created using a same Runnable, 
 * the instance variable of the Runnable are shared.
 * 
 *   count variable will be updated in each thread.
 *  
 *
 */
public class SingleRunnable {

	public static void main(String[] args) {
		RunnableShare r = new RunnableShare();
		Thread t1 = new Thread(r,"T1");
		Thread t2 = new Thread(r,"T2");
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Count = " + r.getCount());
		System.out.println("Copies = " + r.getCopies());
	}

}
