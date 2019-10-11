package example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Worker1 w1 = new Worker1();
		Callable<Integer> w2 = new Worker2();
		
		ExecutorService exec = Executors.newFixedThreadPool(2);
		
		Future<?> f1 = exec.submit(w1);
		Future<Integer> f2 = exec.submit(w2);
		try {
			f1.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Integer r1 = w1.getValue();
	
		Integer r2 = f2.get();
		
		System.out.println("Done");
		System.out.println(r1);
		System.out.println(r2);
		exec.shutdown();
		//exec.submit(w1);

	}

}
