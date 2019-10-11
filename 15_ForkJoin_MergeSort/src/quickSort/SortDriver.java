package quickSort;
import java.util.Random;

import intArraySortUtils.*;

/**
 * Test harness for parallel-sorting routines.
 * 
 *
 */
public class SortDriver {
	
	final static int NUMELTS = 20_000_000;  		// Number of elements in array (1,000,000)
	final static int NUMCPUS = Runtime.getRuntime().availableProcessors();

	/**
	 * Routine for running, timing sort routines.  Sort is non-destructive.
	 * @param s			Sorting object
	 * @param origElts	Array to sort
	 * @return			Sorted copy of origElts
	 */
	public static int[] runIntSort (IntSort s, int[] origElts){
		
		String sortName = s.getClass().getSimpleName(); // Get name of class

		// Create copy of original array to work on
		int[] elts = new int[origElts.length];
		System.arraycopy(origElts, 0, elts, 0, origElts.length);
		
		// Print sort name, then compute, print compute time
		System.out.printf("%-33s starts ... ", sortName);
		long start = System.nanoTime();
		s.sort(elts);
		long end = System.nanoTime();
		System.out.printf("finishes in %,.2f milliseconds.%n", (end-start)/1000000.0);
		
		return elts;

	}

	/**
	 * Main routine
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Create a random array with NUMELTS elements
		
		final int[] origElts = new int[NUMELTS];
		Random r = new Random();
		for (int i = 0; i < NUMELTS; i++) {
			origElts[i] = r.nextInt();
		}
				
		// Create sorting objects.
		
		IntSort QS = new QuickSort();
		IntSort SS = new SelectionSort();
		IntSort PQSEC = new ParallelQuickSortEltCount ();
		IntSort PQSECT = new ParallelQuickSortEltCountTunable ();
		IntSort PQSSD = new ParallelQuickSortShutDown ();
		IntSort PQSSDT = new ParallelQuickSortShutDownTunable ();
		IntSort PQSTC = new ParallelQuickSortTaskCount ();
		IntSort PQSTCT = new ParallelQuickSortTaskCountTunable ();
		IntSort FJQS = new ForkJoinQuickSort ();
		
		// Print number of CPUs, elements
		
		System.out.printf("Number of CPUs:  %d%n", NUMCPUS);
		System.out.printf("Number of elements to sort:  %,d%n",NUMELTS);
		
		// Run the sorting objects.
		
		runIntSort(QS, origElts);
		//runIntSort(SS, origElts);
		runIntSort(PQSEC, origElts);
		runIntSort(PQSECT, origElts);
		runIntSort(PQSSD, origElts);
		runIntSort(PQSSDT, origElts);
		runIntSort(PQSTC, origElts);
		runIntSort(PQSTCT, origElts);
		runIntSort(FJQS, origElts);
	}

}
