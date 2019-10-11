package comletion;
/*
 * With  completion servcice, the shortest task 
 * will be  printed first. So tasks be printed from 
 * shortest to longest order. 
 * 
 *  Without  completion service, tasks are printed 
 *  in the order they are submitted. One long task 
 *  can delay the result  of short  tasks  after  it.
 * 
 */
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceDemo {

	public static void main(String[] args) {
		int nThreads = 10;
		ExecutorService executor = Executors.newFixedThreadPool(nThreads);
		CompletionService<String> compl = new ExecutorCompletionService<String>(executor);
		System.out.println("Completion Service");
		for(int i = 1; i <= nThreads; i++) {
			Task task = new Task();
			compl.submit(task);
		}
		try {
			for(int i = 1; i <= nThreads; i++) {
				Future<String> f = compl.take();
				System.out.println(f.get());
			}
     	} catch (InterruptedException e) {
             Thread.currentThread().interrupt();
     	} catch (ExecutionException e) {
             Thread.currentThread().interrupt();
     	} 
		
		// shortest one will be printed first 
		
		
		System.out.println("Executor service");
		
		Future[] flist = new Future[nThreads];
		for(int i = 0; i < nThreads; i++) {
			Task task = new Task();
			flist[i] =  executor.submit(task);
		}
		try {
			for(int i = 0; i < nThreads; i++) {
				System.out.println(flist[i].get());
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
