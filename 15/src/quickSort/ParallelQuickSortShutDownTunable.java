package quickSort;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import intArraySortUtils.IntArraySortUtils;
import intArraySortUtils.IntSort;

/**
 * 
 * Tuned version of parallel quick sort routine given in ParallelQuickSortShutDown.
 * The difference with that routine is that a threshold is used to shift sorting
 * from parallel to sequential.
 *
 */
public class ParallelQuickSortShutDownTunable implements IntSort {
	
	private ExecutorService exec;	// Executor to run sorting tasks
	private int THRESHOLD;			// Tasks below this size are handled sequentially
	
	/**
	 * Private class of sorting tasks.  Each task involves sorting a given array
	 * segment sequentially.
	 */
	private class PQSTask implements Runnable {
		private int elts[];
		private int first;
		private int size;
		
		public PQSTask (int elts[], int first, int size) {
			this.elts = elts;
			this.first = first;
			this.size = size;
		}
		
		public void run () {
			IntArraySortUtils.quickSortSegment (elts, first, size);
		}
	}
	
	/**
	 * Method for sorting an array segment.  If the size of the segment is small
	 * enough, create a sequential sorting task and give it to the executor.
	 * Otherwise, partition segment, then recursively sort two parts.  When a call
	 * terminates, all tasks needed to complete sorting are guaranteed to have been
	 * submitted to exec.
	 * 
	 * @param elts	Backing array for segment
	 * @param first	Initial position of segment in backing array
	 * @param size	# of elements in backing array.
	 */
	public void parallelQuickSortSegment (int[] elts, int first, int size) {
		if (size <= THRESHOLD) {
			exec.execute(new PQSTask (elts, first, size));
		}
		else {
			int pivotPosition = IntArraySortUtils.partitionSegment(elts, first, size);
			parallelQuickSortSegment (elts, first, pivotPosition-first);
			parallelQuickSortSegment (elts, pivotPosition+1, size-(pivotPosition+1-first));
		}
	} 
	
	/* (non-Javadoc)
	 * 
	 * Method implementing quicksort.  The number of threads is computed and used to
	 * determine the threshold segment size under which sorting is done sequentially.
	 * Then the segment-sorting routine is called; when this terminates, no new tasks
	 * can be submitted, so we shut down the executor and await its termination in
	 * order to determine when sorting is finished.
	 * 
	 * @see quickSort.IntSort#sort(int[])
	 */
	public void sort (int[] elts) {
		int NUMTHREADS = Runtime.getRuntime().availableProcessors();
		exec = Executors.newFixedThreadPool(NUMTHREADS);
		THRESHOLD = elts.length / NUMTHREADS;
		parallelQuickSortSegment (elts, 0, elts.length);
		exec.shutdown();
		try {
			exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) { }
	}
}
