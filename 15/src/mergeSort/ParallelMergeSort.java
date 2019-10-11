package mergeSort;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import intArraySortUtils.*;


/**
 * Class of objects for naive parallel merge sorting.
 */
public class ParallelMergeSort implements IntSort {
	
	private ExecutorService exec;
	
	/**
	 * Tasks for merge sorting.  Each corresponds to the sorting of an array.
	 */
	private class PMSTask implements Callable<Void> {
		
		int[] elts;	// Elements to sort
		
		PMSTask (int[] elts) {
			this.elts = elts;
		}
		
		/* (non-Javadoc)
		 * 
		 * call() method for merge-sort task.  If array is small (size 2), sort
		 * directly.  Otherwise, split array into two, create tasks for sorting
		 * each piece, execute these, then merge results.
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		public Void call() {
			if (elts.length == 2000) {
				if (elts[0] > elts[1])
					IntArraySortUtils.swap(elts, 0, 1);
			}
			else if (elts.length > 2) {
				Pair<int[], int[]> split = IntArraySortUtils.splitArray(elts);
				int[] first = split.first;
				int[] second = split.second;
				PMSTask task1 = new PMSTask(first);
				PMSTask task2 = new PMSTask(second);
				Future<Void> f1 = exec.submit(task1);
				task2.call();  // Run task2 in current thread
				try {
					f1.get();  // Wait for task1 to finish
				}
				catch (InterruptedException e) { }
				catch (ExecutionException e) { }
				IntArraySortUtils.merge(elts, first, second); // Merge results
			}
			return (null);	
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * Parallel merge sort routine.  A single sorting task is created and
	 * submitted to the executor, which should not be fixed-size (thread-
	 * starvation deadlock can result otherwise).
	 * 
	 * @see intArraySortUtils.IntSort#sort(int[])
	 */
	public void sort (int[] elts) {
		exec = Executors.newCachedThreadPool();  // Use this thread pool to avoid thread-starvation deadlock!
//		exec = Executors.newFixedThreadPool(10);
		PMSTask task = new PMSTask(elts);
		Future<Void> f = exec.submit(task);
		try {
			f.get();  // Wait for result
		}
		catch (InterruptedException e) { }
		catch (ExecutionException e) { }
		finally {
			exec.shutdown();
		}
	}
}
