package parallelSum;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class ParallelIntArraySumCallable {
	private int nWorkers;
	
	private int nTasks;
	
	/**
	 * @param nWorkers - Number of worker threads in thread pool
	 * @param nTasks - Number of tasks to create
	 */
	public ParallelIntArraySumCallable (int nWorkers, int nTasks) {
		this.nWorkers = nWorkers;
		this.nTasks = nTasks;
	}
	
	public int sum (Integer elts[]) {
		
		ExecutorService exec = Executors.newFixedThreadPool(nWorkers); // Single use

		int size = elts.length;
		int taskSize = (size) / nTasks;
		int sum = 0;
		
		IntArraySegmentSumCallableTask[] tasks = new IntArraySegmentSumCallableTask[nTasks];
		
		// Create tasks
		
		for (int i=0; i < nTasks-1; i++) {
			tasks[i] = new IntArraySegmentSumCallableTask (new ArraySegment<Integer>(elts, i*taskSize, taskSize));
		}
		int lastTaskFirst = (nTasks-1) * taskSize;
		int lastTaskSize = size - ((nTasks-1) * taskSize);
		tasks[nTasks-1] = new IntArraySegmentSumCallableTask (new ArraySegment<Integer>(elts, lastTaskFirst, lastTaskSize));
		
		// Submit tasks, storing in collection.
		
		List<Future<Integer>> futures = new ArrayList<Future<Integer>>(nTasks);
		
		for (int i=0; i < nTasks; i++) {
			futures.add(exec.submit(tasks[i]));
		}
		
		// Wait for executor to finish
		
		exec.shutdown();

		// Collect results
		
		try {
			for (Future<Integer> f : futures) {
				sum += f.get();
			}
		}
		catch (InterruptedException e) { }
		catch (ExecutionException e) { }
		
		return sum;
		
	}
}
