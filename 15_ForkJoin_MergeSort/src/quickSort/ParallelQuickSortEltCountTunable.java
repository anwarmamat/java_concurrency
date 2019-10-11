package quickSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import intArraySortUtils.BasicCountingLatch;
import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

/**
 * Optimized version of parallel quicksort implementations given in
 * ParallelQuickSortEltCount. A threshold is used to determine when to switch
 * from generating new sorting tasks to be performed in parallel to using the
 * traditional single-thread implementation.
 * 
 */
public class ParallelQuickSortEltCountTunable implements IntSort {

	private ExecutorService exec; 		// Executor used for sorting tasks
	private BasicCountingLatch done; 	// Tracks number of elements not yet in
										// 	   correct position
	private int THRESHOLD; 				// Determines when problems are small enough
										// 	   to be sorted sequentially

	/**
	 * Class of sorting tasks. A task consists of an array segment (backing
	 * array, first position in backing array of segment, and extent, or size,
	 * of segment).
	 */
	private class PQSTask implements Runnable {
		private int[] elts;
		private int first;
		private int size;

		public PQSTask(int[] elts, int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}

		/**
		 * run() method uses THRESHOLD to determine whether to sort array
		 * segment sequentially or in parallel.
		 */
		public void run() {
			parallelQuickSortSegment(elts, first, size);
		}
	}

	/**
	 * Method for sorting an array segment in parallel
	 * 
	 * @param elts	Backing array for segment
	 * @param first	Starting position in backing array of segment
	 * @param size	Size of segment
	 */
	public void parallelQuickSortSegment(int[] elts, int first, int size) {
		if (size <= THRESHOLD) {
			IntArraySortUtils.quickSortSegment(elts, first, size);
			done.countDown(size);
		} else {
			// Partition array, create two sorting tasks, execute them
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			PQSTask task1 = new PQSTask(elts, first, pivotPosition - first);
			PQSTask task2 = new PQSTask(elts, pivotPosition + 1, size - (pivotPosition + 1 - first));
			done.countDown(); // Pivot is in correct sorted position
			exec.execute(task1);
			// exec.execute(task2);
			task2.run(); // Run second task inside existing worker thread.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Method for sorting an array. THRESHOLD is computed based on number of
	 * available processors.
	 * 
	 * @see quickSort.IntSort#sort(int[])
	 */
	public void sort(int[] elts) {
		
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		
		THRESHOLD = elts.length / NUMTHREADS;
		
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		done = new BasicCountingLatch(elts.length);
		exec.execute(new PQSTask(elts, 0, elts.length));
		done.await(); // Wait until array is sorted (done == 0)
		exec.shutdown();
	}
}
