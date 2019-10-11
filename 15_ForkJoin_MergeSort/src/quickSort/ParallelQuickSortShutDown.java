package quickSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

//

/**
 * Parallelized quicksort using executor shutdown for termination.
 * 
 * Objects consist of a sort method that creates an executor, feeds it tasks,
 * then shuts it down to determine when sorting is done. Tasks are created and
 * submitted using a single-threaded recursive routine; when routine returns
 * there are no more tasks needed, and executor is then shutdown. Termination of
 * executor signals completion of sort.
 *
 */
public class ParallelQuickSortShutDown implements IntSort {

	private ExecutorService exec;	// Executor to run sorting tasks

	/**
	 * Inner class of sorting tasks. In this implementation, a task consists of
	 * swapping two adjacent elements.
	 *
	 */
	private class PQSTask implements Runnable {
		private int elts[];
		private int i;
		private int j;

		public PQSTask(int elts[], int i, int j) {
			this.elts = elts;
			this.i = i;
			this.j = j;
		}

		public void run() {
			IntArraySortUtils.swap(elts, i, j);
		}
	}

	/**
	 * Method for sorting a segment of an array. The structure follows that of
	 * sequential quicksort. Note that new tasks are only given to executor in
	 * "base case" of recursion. This ensures that when a method terminates, all
	 * tasks needed to complete call have been given to executor.
	 * 
	 * @param elts	Backing array for segment
	 * @param first	Initial position of segment in backing array
	 * @param size	Size of segment
	 */
	public void parallelQuickSortSegment(int[] elts, int first, int size) {
		if (size == 2) {
			if (elts[first] > elts[first + 1])
				exec.execute(new PQSTask(elts, first, first + 1));
		} else if (size > 2) {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			parallelQuickSortSegment(elts, first, pivotPosition - first);
			parallelQuickSortSegment(elts, pivotPosition + 1, size - (pivotPosition + 1 - first));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Main sorting method. Note that an executor is created and assigned to the
	 * "exec" field. This executor is used by the segment-sorting routine above.
	 * When the call to the segment-sorting method returns, there can be no more
	 * tasks submitted. In this case, we shut down the executor and wait for it
	 * to terminate; this signals that all sorting is done.
	 * 
	 * @see quickSort.IntSort#sort(int[])
	 */
	public void sort(int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		parallelQuickSortSegment(elts, 0, elts.length);
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) { }
	}
}
