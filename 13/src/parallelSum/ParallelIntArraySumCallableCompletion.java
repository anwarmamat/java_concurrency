package parallelSum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelIntArraySumCallableCompletion {
	private int nWorkers;
	
	private int nTasks;
	
	/**
	 * @param nWorkers - Number of worker threads in thread pool
	 * @param nTasks - Number of tasks to create
	 */
	public ParallelIntArraySumCallableCompletion (int nWorkers, int nTasks) {
		this.nWorkers = nWorkers;
		this.nTasks = nTasks;
	}
	
	public int sum (Integer elts[]) {
		
		// Constructor executor, then wrap inside a completion service.
		ExecutorService execHelper = Executors.newFixedThreadPool(nWorkers);
		ExecutorCompletionService<Integer> exec = new ExecutorCompletionService<Integer>(execHelper); // Single use

		int size = elts.length;
		int taskSize = (size) / nTasks;
		int sum = 0;
		
		// Create and submit tasks
		
		for (int i=0; i < nTasks-1; i++) {
			exec.submit(new IntArraySegmentSumCallableTask (new ArraySegment<Integer>(elts, i*taskSize, taskSize)));
		}
		int lastTaskFirst = (nTasks-1) * taskSize;
		int lastTaskSize = size - ((nTasks-1) * taskSize);
		exec.submit(new IntArraySegmentSumCallableTask (new ArraySegment<Integer>(elts, lastTaskFirst, lastTaskSize)));
		execHelper.shutdown(); // No need for more tasks, so shut it down
		
		// Collect results.  We need to keep track of how many we have gotten so we know when to exit.
		
		try {
			for (int i=0; i < nTasks; i++)
				sum += exec.take().get();
		}
		catch (InterruptedException e) { }
		catch (ExecutionException e) { }
		
		return sum;
		
	}
}
