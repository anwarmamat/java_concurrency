/**
 * This example shows the data race. The counter object is shared between two threads. 
 * Each of the two threads increments the shared counter 1000 times. When all threads 
 * terminate, the value of the counter must be 2000. But, If we do not synchronize 
 * the updating of counter, we may have a counter value that is less than 2000.
 * 
 * @author Anwar Mamat
 *
 */

package counter;

public class Main {

	public static void main(String[] args){
		int n = 1000;
		Counter c1 = new Counter();
		//MyCounter c2 = new MyCounter();
		
		Runnable r1 = new Worker(c1, n);
		Runnable r2 = new Worker(c1, n);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(c1.get()); 
	}
}
