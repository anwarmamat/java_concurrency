package mergeSort;
import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import intArraySortUtils.*;

/**
 * Performance-tuned parallelized merge sort.  The basic idea is to switch to
 * single-threaded merge sort when the problems sizes are below a certain
 * threshold.
 */
public class ParallelMergeSortTunable implements IntSort {
	private ExecutorService exec;
	private int THRESHOLD; // Integer used to determine when to switch to sequential
		
	/**
	 * Class of tasks for parallel merge sort.
	 */
	private class PMSTask implements Callable<Void> {
		
		int[] elts;
		
		PMSTask (int[] elts) {
			this.elts = elts;
		}
		
		/* (non-Javadoc)
		 * 
		 * call() method for merge-sort tasks.  If the size of the array to be
		 * sorted is below THRESHOLD, invoke single-threaded sorting routine.
		 * Otherwise, split array into two, create tasks for sorting each, run
		 * them, then merge results.
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		public Void call () {
			// Small list
			if (elts.length <= THRESHOLD) {
				IntArraySortUtils.mergeSort(elts);
			}
			else { // Large list
				Pair<int[], int[]> split = IntArraySortUtils.splitArray(elts);
				int[] first = split.first;
				int[] second = split.second;
				PMSTask task1 = new PMSTask (first);
				PMSTask task2 = new PMSTask (second);
				Future<Void> f1 = exec.submit(task1);
				task2.call();	// Run in current thread
				try {
					f1.get();	// Wait for task1 result
				}
				catch (InterruptedException e) {}
				catch (ExecutionException e) {}
				IntArraySortUtils.merge(elts, first, second);
			}
			return null;
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
		int NUMCPUS = Runtime.getRuntime().availableProcessors();
		int size = elts.length;
		THRESHOLD = size / NUMCPUS / 10;  // Size for switching to sequential computation
		exec = Executors.newCachedThreadPool();
		Future<Void> result = exec.submit(new PMSTask (elts));
		try {
			result.get();  // Wait for result
		}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {}
		finally {
			exec.shutdown();
		}
	}

}
