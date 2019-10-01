package threads;

/**
 * Simple hello world program, using threads.
 */
public class HelloWorldRunner {

	public static void main(String[] args) throws InterruptedException {
		Thread h1 = new HelloWorldThread ();
		Thread h2 = new Thread (new HelloWorldRunnable ());
		
//		System.out.println(h1.getState());
//		h1.start();
//		h1.join();
//		System.out.println(h1.getState());		
//		h1.run();
//		System.out.println("h1 thread state is " + h1.getState());
		h1.start();
		h2.start();
//		System.out.println("h1 thread state is " + h1.getState());
//		h1.start();
//		h1.run();
//		h2.run ();
//		h2.start ();
//		try {
//			Thread.sleep (100);
//		}
//		catch (InterruptedException e) {}
		h1.join();
		h2.run();
//		h2.join();
//		h1.run();
//		System.out.println ("h1 thread state is " + h1.getState());
//		h2.run();
		System.out.println("main Done");

		
	}
}
