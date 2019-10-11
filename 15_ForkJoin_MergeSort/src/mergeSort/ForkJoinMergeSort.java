package mergeSort;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import intArraySortUtils.*;

/**
 * Parallelized merge sort implemented using Fork/Join

 */
public class ForkJoinMergeSort implements IntSort {
	
	private ForkJoinPool fjPool;
	private int THRESHOLD;
	
	/**
	 * Fork/join tasks for merge sorting a given array
	 */
	@SuppressWarnings("serial")
	private class FJMSTask extends RecursiveAction {
		
		private final int[] elts;  // Elements to be sorted
		
		FJMSTask (int[] elts) {
			this.elts = elts;
		}
		
		/* (non-Javadoc)
		 * 
		 * Compute method.  If the size is "small enough", sort sequentially.
		 * Otherwise, split the array create two tasks, run them, and merge
		 * results.
		 * 
		 * @see java.util.concurrent.RecursiveAction#compute()
		 */
		public void compute () {
			if (elts.length <= THRESHOLD)
				IntArraySortUtils.mergeSort(elts);
			else {
				Pair<int[], int[]> split = IntArraySortUtils.splitArray(elts);
				int[] first = split.first;
				int[] second = split.second;
				FJMSTask task1 = new FJMSTask (first);
				FJMSTask task2 = new FJMSTask (second);
				
				task1.fork();
				task2.compute();	// Can run compute() in current thread
				task1.join();		// Wait for task1 to finish
				
				
				IntArraySortUtils.merge(elts, first, second);  // Merge results
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * Method for parallel merge sorting.
	 * 
	 * @see intArraySortUtils.IntSort#sort(int[])
	 */
	public void sort (int[] elts) {
		int NUMCPUS = Runtime.getRuntime().availableProcessors();
		int size = elts.length;
		THRESHOLD = size / NUMCPUS / 10;
		fjPool = new ForkJoinPool();
		RecursiveAction task = new FJMSTask(elts);
		fjPool.execute(task);
		task.join();
		fjPool.shutdown();
	}
}
