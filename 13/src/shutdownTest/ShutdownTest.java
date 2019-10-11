package shutdownTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class showing how executor shutdown status works.  An exception may be thrown if anonymous task
 * is submitted after executor is shut down.
 * 
 *
 */
public class ShutdownTest {

	public static void main(String[] args) {

		final ExecutorService exec = Executors.newFixedThreadPool(2);
		Runnable task1 = new Runnable() {
			public void run () {
				System.out.println("Task 1 executing");
			}
		};
		exec.execute(task1);
		
		Runnable task2 = new Runnable() {
			public void run () {
				System.out.println("Task 2 starting");

				System.out.println("Executor shutdown status = " + exec.isShutdown());
				exec.execute(new Runnable() {
					public void run () {
						System.out.println("Anonymous task running");
					}
				});
			}
		};
		exec.execute(task2);
		try {
			Thread.sleep(20);
		}
		catch (InterruptedException e) { }
		exec.shutdown();
	}

}
