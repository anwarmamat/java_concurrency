package callableRunnable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableRunnable {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
	final ExecutorService exec = Executors.newFixedThreadPool(2);
	
	Worker1 r = new Worker1();
	Callable<Integer> c = new Worker2();
	
	Future<?> w1  = exec.submit(r);
	
	Future<Integer> future = exec.submit(c);
	
	Integer result;

	result = future.get();
	w1.get();
	
	System.out.println("Done:");
	System.out.println(result);
	System.out.println(r.getValue());
	
	exec.shutdown();
	}
}
