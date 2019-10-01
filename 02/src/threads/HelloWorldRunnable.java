package threads;

/**
 * Class using Runnable interface to help implement
 * threads printing "Hello World!"
 */
public class HelloWorldRunnable implements Runnable {
	//more code
	public void run () {
		
		System.out.println (Thread.currentThread() + " Runnable says Hello World!");
	}
	
	//more methods
}
