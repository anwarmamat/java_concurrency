package quickSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

/**
 * Fork/join implementation of quicksort.
 */
public class ForkJoinQuickSort implements IntSort {

	private ForkJoinPool fjPool;
	private int THRESHOLD;
	
	/**
	 * Fork/join tasks for sorting.  Each task is responsible for sorting a segment
	 * of the over-all array being sorted.
	 */
	@SuppressWarnings("serial") // Needed to suppress annoying warning
	private class FJQSTask extends RecursiveAction {
		private int[] elts;
		private int first;
		private int size;
		
		public FJQSTask (int[] elts, int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}

		/* (non-Javadoc)
		 * 
		 * compute() method of fork/join task.  A threshold is used to determine
		 * whether the array is small enough to be sorted sequentially.
		 * 
		 * @see java.util.concurrent.RecursiveAction#compute()
		 */
		protected void compute() {
			if (size <= THRESHOLD) {
				IntArraySortUtils.quickSortSegment(elts, first, size);
			}
			else {
				
				// If array is too big, partition it, create tasks for sorting
				// each partition, run these.
				
				int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
				FJQSTask task1 = new FJQSTask(elts, first, pivotPosition-first);
				FJQSTask task2 = new FJQSTask(elts, pivotPosition+1, size-(pivotPosition+1-first));
				
				task1.fork();		
		     	task2.compute();  	// Run task 2 in current thread.
		    	task1.join();		// Wait for task 1 to finish.	
				
				
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * Sorting routine for array.  A single fork/join task is created and given to
	 * the fork/join pool.
	 * 
	 * @see intArraySortUtils.IntSort#sort(int[])
	 */
	public void sort(int[] elts) {
		int NUMCPUS = Runtime.getRuntime().availableProcessors();
		THRESHOLD = elts.length / NUMCPUS;
		fjPool = new ForkJoinPool();
		FJQSTask task = new FJQSTask(elts, 0, elts.length);
		fjPool.execute(task);
		task.join(); // Wait for task to finish
		fjPool.shutdown();
	}

}
