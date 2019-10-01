package threads;

/**
 * Class of threads for printing "Hello World", implemented using inheritance.

 */
public class HelloWorldThread extends Thread {
	public void run () {
		
		try {
			sleep(1000);
			//calculation
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return;
		}
		
		System.out.println (Thread.currentThread() + " prints:  Thread says Hello World!");
	}
}
