package intArraySortUtils;

/**
 * Class of objects implementing single-threaded quicksort.
 * 
 */
public class QuickSort implements IntSort {

	@Override
	public void sort(int[] elts) {
		IntArraySortUtils.quickSort(elts);
	}

}
