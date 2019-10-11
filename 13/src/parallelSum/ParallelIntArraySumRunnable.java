package parallelSum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
* 
 * Sums an array by breaking into Runnable tasks that store results in
 * another array, then summing that array.
 *
 */
public class ParallelIntArraySumRunnable {
	
	private int nWorkers;
	
	private int nTasks;
	
	/**
	 * @param nWorkers - Number of worker threads in thread pool
	 * @param nTasks - Number of tasks to create
	 */
	ParallelIntArraySumRunnable (int nWorkers, int nTasks) {
		this.nWorkers = nWorkers;
		this.nTasks = nTasks;
	}
	
	public int sum (Integer elts[]) {
		
		ExecutorService exec = Executors.newFixedThreadPool(nWorkers); // Single use

		int size = elts.length;
		//int taskSize = (size + nTasks - 1) / nTasks; // Only works if nTasks <= elts.size
		int taskSize = (size) / nTasks;
		int[] results = new int[nTasks];
		
		IntArraySegmentSumRunnableTask[] tasks = new IntArraySegmentSumRunnableTask[nTasks];
		
		// Create tasks
		
		for (int i=0; i < nTasks-1; i++) {
			tasks[i] = new IntArraySegmentSumRunnableTask (new ArraySegment(elts, i*taskSize, taskSize), results, i);
		}
		int lastTaskFirst = (nTasks-1) * taskSize;
		int lastTaskSize = size - ((nTasks-1) * taskSize);
		tasks[nTasks-1] = new IntArraySegmentSumRunnableTask (new ArraySegment(elts, lastTaskFirst, lastTaskSize), results, nTasks-1);
		
		// Submit tasks
		
		for (int i=0; i < nTasks; i++) {
			exec.execute(tasks[i]);
		}
		
		// Wait for executor to finish
		
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) { }
		
		// Collect results
		
		int sum=0;
		for (int i=0; i < nTasks; i++) sum += results[i];
		
		// Return
		
		return sum;
		
	}
	
}
