package callableRunnable;
/**
 *  An example for Runnable and Callable
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableRunnableMain {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
	final ExecutorService exec = Executors.newFixedThreadPool(2);
	
	// create a runnable
	Runnable_Worker r = new Runnable_Worker();
	
	//create a callable
	Callable<Integer> c = new Callable_Worker();
	
	//submit the runnable
	Future<?> w1  = exec.submit(r);
	
	//submit the callbale, which can return an Integer
	Future<Integer> future = exec.submit(c);
	
	Integer result;

	// get the return value of the callable
	result = future.get();
	
	
	// runnable does not return a value. Future get() blocks here,
	// but does not return a value
	w1.get();
	// call the getvalue of the runnable to get the result
	Integer result2 = r.getValue();
	
	System.out.println("Done:");
	System.out.println(result);
	System.out.println(result2);
	
	exec.shutdown();
	}
}
