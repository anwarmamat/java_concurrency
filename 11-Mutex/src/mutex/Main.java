/**
 * This example shows that a single thread can create deadlock 
 * if the the Lock is not reentrant. 
 * 
 * 
 * @author Anwar Mamat
 *
 */

package mutex;

public class Main {

	public static void main(String[] args){
		int n = 10000;
		int numOfThreads = 10;
		Counter c1 = new Counter();
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
		} 
		System.out.println(c1.get()); 
	}
}
