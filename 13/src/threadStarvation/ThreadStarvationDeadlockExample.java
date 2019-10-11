package threadStarvation;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Simple example illustrating thread-starvation deadlock.
 * If nThreads is 1, there is a deadlock.
 * If nThreads is > 1, there is no deadlock.
 * 
 *
 */
public class ThreadStarvationDeadlockExample {

	public static void main(String[] args) {
		final int nThreads = 1;
		final ExecutorService exec = Executors.newFixedThreadPool(nThreads);
		
		final Callable<String> task1 = new Callable<String>() {
			public String call() {
				System.out.println("t1 is submitted.");
				return "foo";
			}
		};
		
		final Callable<String> task2 = new Callable<String>() {
			public String call() {
				System.out.println("t2 is submitted.");
				//task 2 submits new task task1
				Future<String> future1 = exec.submit(task1);
				try {
					System.out.println(future1.get());
					return "bar";
				}

				catch (InterruptedException e) { return "bar"; }
				catch (ExecutionException e) { return "bar"; }
				finally {}
			}
		};
		
		Future<String> future2 = exec.submit(task2);
		try {
			System.out.println(future2.get());
		}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {}
		finally {exec.shutdown();}
	}

}
