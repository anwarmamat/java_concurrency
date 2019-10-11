package executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo1 {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
		    String threadName = Thread.currentThread().getName();
		    System.out.println("Hello " + threadName);
		});
		
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
		Callable<Integer> task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(1);
		        return 123;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		};
		
		ExecutorService executor2 = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor2.submit(task);

		System.out.println("future done? " + future.isDone());
		System.out.println("future done? " + future.isDone());
		
		Integer result  = 0;
		try {
			result = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("future done? " + future.isDone());
		System.out.print("result: " + result);

		executor2.shutdownNow();
		
		
		//invokeall
		
		ExecutorService executor3 = Executors.newWorkStealingPool();

		List<Callable<String>> callables2 = Arrays.asList(
		        () -> "task1",
		        () -> "task2",
		        () -> "task3")
				;

		executor.invokeAll(callables2)
		    .stream()
		    .map(future -> {
		        try {
		            return future.get();
		        }
		        catch (Exception e) {
		            throw new IllegalStateException(e);
		        }
		    })
		    .forEach(System.out::println);
		
		//invoke any
		
		
		Callable<String> callable(String result,  long sleepSeconds) {
		    return () -> {
		        TimeUnit.SECONDS.sleep(sleepSeconds);
		        return result;
		    };
		};
		//Supplier<Foo> makeFooFromString(String str) { return () -> new Foo(str); }
		
		/**
		Callable<String> callable = () -> {
		    // Perform some computation
		    Thread.sleep(2000);
		    return "Return some result";
		};

		Callable<String> c = () -> "Hello you!";
		*/
		

		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
		    callable("task1", 2),
		    callable("task2", 1),
		    callable("task3", 3));

		String result = executor.invokeAny(callables);
		System.out.println(result);

		// 
	}

}
