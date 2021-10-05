package threadStarvation;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * An example illustrating thread-starvation deadlock.
 * Task 2 submits Task 1, and Task 1 submits Task 0.

 * If nThreads is 2, there is a deadlock.
 * If nThreads is > 2, there is no deadlock.
 * 
 */
public class ThreadStarvationDeadlockExample {

	public static void main(String[] args) {
		final int nThreads = 3;
		final ExecutorService exec = Executors.newFixedThreadPool(nThreads);
		
		final Callable<String> task0 = new Callable<String>() {
			public String call() {
				System.out.println("t0 is submitted.");
				return "task 0 done";
			}
		};
		
		final Callable<String> task1 = new Callable<String>() {
			public String call() {
				System.out.println("t1 is submitted.");
				Future<String> future1 = exec.submit(task0);
				try {
					System.out.println(future1.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				return "task 1 done";
			}
		};
		
		final Callable<String> task2 = new Callable<String>() {
			public String call() {
				System.out.println("t2 is submitted.");
				//task 2 submits 2 new tasks of task1
				Future<String> future1 = exec.submit(task1);
				try {
					System.out.println(future1.get());
					return "task 2 done";
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
