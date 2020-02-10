/**
 * This example shows that a single thread can create deadlock 
 * if the the Lock is not reentrant. 
 * 
 * 
 * @author Anwar Mamat
 *
 */

package nonreentrant;

public class Main {

	public static void main(String[] args){
		int n = 1000;
		int numOfThreads = 10;
		/*
		 * 	Counter_NonReentrant will create deadlock
		 */
		Counter c1 = new Counter_NonReentrant();
		
		/*
		 * Counter_Reentrant will not create deadlock.
		 */
		//Counter c1 = new Counter_Reentrant();
		Runnable r1 = new Worker(c1, n);
		Thread[] workers = new Thread[numOfThreads];
		for(int i = 0; i < numOfThreads; i++) {
			workers[i] = new Thread(r1);
			workers[i].start();
		}
		try {
			for(int i = 0; i < numOfThreads; i++) {
				workers[i].join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println(c1.get()); 
	}
}
