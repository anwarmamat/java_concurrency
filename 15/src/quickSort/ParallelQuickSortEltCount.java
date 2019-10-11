package quickSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import intArraySortUtils.BasicCountingLatch;
import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

/**
 * Naive parallel implementation of quicksort, using a latch to track # of
 * elements not yet in correct final position. When this count is 0, array is
 * sorted.
 */
public class ParallelQuickSortEltCount implements IntSort {

	private ExecutorService exec;	 	// Executor used for sorting tasks
	private BasicCountingLatch done; 	// Tracks number of elements not yet in
									 	// 	   correct position

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

		public void run() {
			parallelQuickSortSegment(elts, first, size);
		}
	}

	/**
	 * Method for parallel quick sort of array segment. Note that the number of
	 * elements in correct position must be maintainned.
	 * 
	 * @param elts	Backing array
	 * @param first	Initial position of segment
	 * @param size	Size of segment
	 */
	public void parallelQuickSortSegment(int[] elts, int first, int size) {
		if (size == 1) {
			done.countDown(); // Element is in correct position.
		}
		else if (size == 2) {
			if (elts[first] > elts[first + 1])
				IntArraySortUtils.swap(elts, first, first + 1);
			done.countDown(2); // Two elements in correct position.
		} 
		else if (size > 2) {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			PQSTask task1 = new PQSTask(elts, first, pivotPosition - first);
			PQSTask task2 = new PQSTask(elts, pivotPosition + 1, size - (pivotPosition + 1 - first));
			done.countDown(); // Pivot is in correct sorted position
			exec.execute(task1);
			// exec.execute(task2);
			task2.run(); // Run second task in existing worker thread
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Sorting routine. Note that the "done" latch is used to delay termination
	 * until all the elements are in their correct position.
	 * 
	 * @see intArraySortUtils.IntSort#sort(int[])
	 */
	public void sort(int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		done = new BasicCountingLatch(elts.length);
		exec.execute(new PQSTask(elts, 0, elts.length));
		done.await(); // Wait for tasks to finish.
		exec.shutdown();
	}

}
