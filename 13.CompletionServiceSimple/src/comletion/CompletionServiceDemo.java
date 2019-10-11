package comletion;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceDemo {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		CompletionService<String> compl = new ExecutorCompletionService<String>(executor);
		
		for(int i = 1; i <= 5; i++) {
			Task task = new Task();
			compl.submit(task);
		}
		try {
			for(int i = 1; i <= 5; i++) {
				Future<String> f = compl.take();
				System.out.println(f.get());
			}
     	} catch (InterruptedException e) {
             Thread.currentThread().interrupt();
     	} catch (ExecutionException e) {
             Thread.currentThread().interrupt();
     	} finally {
             if (executor != null) {
                     executor.shutdownNow();
             }
     	}
		
	}

}
