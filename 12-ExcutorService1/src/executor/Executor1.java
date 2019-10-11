package executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Executor1 {

	public static void main(String[] args) {
		ExecutorService executor =  Executors.newFixedThreadPool(2);//Executors.newSingleThreadExecutor();
		Callable task = new Worker1();
		Future<String> future = executor.submit(task);
		executor.submit(task);
		executor.submit(task);
		
		//executor.shutdown();
		
		boolean r = executor.isTerminated();
		if(r) {
			System.out.println("executor is down");
		}else {
			System.out.println("executor is running");
		}
		
//		try {
//			System.out.println(future.get());
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ExecutionException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
		//System.out.println("shutting down the service");
		//executor.shutdownNow();
		
		
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		r = executor.isTerminated();
		if(r) {
			System.out.println("executor is down");
		}else {
			System.out.println("executor is running");
		}
		
		
//		try {
//			future = executor.submit(task);
//		}catch(Exception e) {
//			System.err.println(e.getMessage());
//		}
	}

}
