package intArraySortUtils;

/**
 * Class of objects implementing single-threaded mergesort.
 * 
 */
public class MergeSort implements IntSort {

	@Override
	public void sort(int[] elts) {
		IntArraySortUtils.mergeSort(elts);
	}

}
