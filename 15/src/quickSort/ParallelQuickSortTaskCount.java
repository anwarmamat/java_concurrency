package quickSort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import intArraySortUtils.BasicCountingLatch;
import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

/**
 * Naive parallel implementation of quicksort.  Uses latch to count number of currently uncompleted
 * tasks; when # is 0, sort is finished.
 * 
 */
public class ParallelQuickSortTaskCount implements IntSort {
	
	private ExecutorService exec;		// Used for running sorting tasks
	private BasicCountingLatch tasks;   // Tracks number of tasks created but not completed.
	
	/**
	 * Tasks for quick sort.  Each corresponds to the sorting of a segment of
	 * the over-all array being sorted.
	 */
	private class PQSTask implements Runnable {
		private int[] elts;	// Backing array
		private int first;	// First position of segment
		private int size;	// # of elements in segment
		
		public PQSTask (int[] elts, int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}
		
		public void run() {
			parallelQuickSortSegment (elts, first, size);
			tasks.countDown(); // Task finishes, so reduce task count
		}
	}
	
	/**
	 * Method for parallel quick sort of array segment.
	 * 
	 * @param elts	Backing array
	 * @param first	Initial position of segment
	 * @param size	# of elements in segment
	 */
	public void parallelQuickSortSegment (int[] elts, int first, int size) {
		if (size == 2) {
			if (elts[first] > elts[first+1])
				IntArraySortUtils.swap (elts, first, first+1);
		}
		else if (size > 2) {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);

			// Create new sorting tasks and increment task count
			PQSTask task1 = new PQSTask(elts, first, pivotPosition-first);
			PQSTask task2 = new PQSTask(elts, pivotPosition+1, size-(pivotPosition+1-first));
			tasks.countUp(2);
			
			// Execute tasks
			exec.execute(task1);
			exec.execute(task2);
//			task2.run(); // Run second task in existing worker thread
		}
	}

	/* (non-Javadoc)
	 * 
	 * Sorting routine.  Note that the "done" latch is used to delay termination
	 * until all the elements are in their correct position.
	 * 
	 * @see intArraySortUtils.IntSort#sort(int[])
	 */
	public void sort(int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		tasks = new BasicCountingLatch(1);
		exec.execute (new PQSTask (elts, 0, elts.length));
		tasks.await();	// Wait for tasks to finish.
		exec.shutdown();
	}
	

}
