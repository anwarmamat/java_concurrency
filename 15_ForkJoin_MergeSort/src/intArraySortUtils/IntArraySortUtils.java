package intArraySortUtils;

/**
 * Class of static methods that support different sorting methods for arrays of ints.
 * 
 */
public class IntArraySortUtils {
	
	/**
	 * Method for swapping elements in array.
	 * 
	 * Precondition:  Both positions are valid positions in the array.
	 * Postcondition:  elements at two positions are swapped.
	 * Exeception:  ArrayIndexOutOfBoundsException may be thrown if either index is invalid.
	 * 
	 * @param elts	Array
	 * @param i		First position in elts
	 * @param j		Second position in elts
	 */
	public static void swap (int[] elts, int i, int j) {
		int temp = elts[i];
		elts[i] = elts[j];
		elts[j] = temp;
	}
	
	/**
	 * Method for finding index of minimum element in a segment of an array.
	 * @param elts	Backing array
	 * @param first	Initial position of segment
	 * @param size	Number of elements in segment
	 * @return		Position of smallest value in segment
	 * 
	 * Precondition:  All positions are valid, size is > 0
	 * Postcondition:  Element at returned position is smallest in segment
	 * Exception:
	 * 	. If positions are invalid, ArrayIndexOutOfBoundsException can be thrown.
	 * 	. If size is <= 0, element at first position is returned.
	 */
	public static int findMinPositionSegment (int[] elts, int first, int size) {
		int min = elts[first];
		int minPosition = first;
		for (int i = first+1; i < first+size; i++) {
			if (elts[i] < min) {
				min = elts[i];
				minPosition = i;
			}
		}
		return minPosition;
	}
	
	/**
	 * Implements destructive selection sort.
	 * 
	 * @param elts	Array to be sorted.
	 */
	public static void selectionSort (int[] elts) {
		int minPosition;
		int length = elts.length;
		for (int i = 0; i < length-1; i++) {
			minPosition = findMinPositionSegment (elts, i, length-i);
			if (minPosition != i)
				swap (elts, i, minPosition);
		}
	}
	
	/**
	 * Splits array into two equal-size pieces (first piece is one smaller than second).
	 * 
	 * @param elts	Array to be split
	 * @return		Pair of arrays; concatenation of first, second yields elts
	 */
	public static Pair<int[], int[]> splitArray (int[] elts) {
		int size = elts.length;
		int mid = size / 2;
		int[] left = new int[mid];
		int[] right = new int [size - mid];
		// Copy
		for (int i = 0; i < mid; i++) {
			left[i] = elts[i];
		}
		for (int i = 0; i < right.length; i++) {
			right[i] = elts[i+mid];
		}
		// Return
		return (new Pair<int[], int[]>(left, right));
	}
	
	/**
	 * Merges arrays a1, a2 into result.  If a1 and a2 are sorted, then result is
	 * sorted.
	 * 
	 * @param result	result of merging a1, a2
	 * @param a1		first array to merge
	 * @param a2		second array to merge
	 * 
	 * Precondition:  length of result is >= length of a1 + length of a2
	 * Postcondition:  elements of a1, a2 merged into first elements of result
	 * Exception:  ArrayIndexOutOfBoundsException raise if result is too short
	 */
	public static void merge (int[] result, int[] a1, int[] a2) {
		int len1 = a1.length;
		int len2 = a2.length;
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		while (i < len1 && j < len2) {
			if (a1[i] < a2[j]) {
				result[k] = a1[i];
				i++;
			}
			else {
				result[k] = a2[j];
				j++;
			}
			k++;
		}
		while (i < len1) {
			result[k] = a1[i];
			i++;
			k++;
		}
		while (j < len2) {
			result[k] = a2[j];
			j++;
			k++;
		}
	}
	
	/**
	 * Implements destructive merge sort.
	 * 
	 * @param elts	Array to sort; holds result when method terminates.
	 */
	public static void mergeSort(int[] elts) {
		int size = elts.length;
		if (size == 2) {
			if (elts[0] > elts[1])
				IntArraySortUtils.swap(elts,  0, 1);
		}
		else if (size > 2) {
			Pair<int[], int[]> split = splitArray(elts);
			int[] left = split.first;
			int[] right = split.second;
			mergeSort(left);
			mergeSort(right);
			IntArraySortUtils.merge(elts, left, right);
		}

	}

	/**
	 * Implements quick-sort partitioning on array segments, with first element in
	 * segment being used as pivot.
	 * 
	 * @param elts	Backing array
	 * @param first	Initial position of segment
	 * @param size	# of elements in segment
	 * @return	Position of pivot after partitiong
	 * 
	 * Precondition:  first, ..., first+size-1 are valid indices
	 * Postcondition:  elts[first ... first+size-1] are rearranged so that
	 * 		original e[first] is preceded by all elements <= to it and followed
	 * 		by all elements > it.
	 * Exception:  if the precondition is violated an exception is thrown
	 */
	public static int partitionSegment (int[] elts, int first, int size) {
		int pivotPosition = first;
		int firstGreaterPosition = first+size;
		while (pivotPosition  < firstGreaterPosition-1) {
			if (elts[pivotPosition] >= elts[pivotPosition+1]) {
				swap (elts, pivotPosition, pivotPosition+1);
				pivotPosition++;
			}
			else {
				firstGreaterPosition--;
				swap (elts, pivotPosition+1, firstGreaterPosition);
			}
		}
		// System.out.println("PartitionSegment terminates.");
		return pivotPosition;
	}
	
	/**
	 * Implements destructive quicksort of given array segment.
	 * 
	 * @param elts	Backing array
	 * @param first	Initial position of segment in elts
	 * @param size	# of elements in segment
	 * 
	 * Precondition:  first, first+size-1 are valid indices in the given array
	 * Postcondition:  array segment defined by first, size is sorted in ascending order
	 * Exception:  if the precondition does not hold then an exception may be thrown
	 */
	public static void quickSortSegment (int[] elts, int first, int size) {
		if (size == 2) {
			if (elts[first] > elts[first+1])
				swap (elts, first, first+1);
		}
		else if (size > 2) {
			int pivotPosition = partitionSegment(elts, first, size);
			quickSortSegment (elts, first, pivotPosition-first);
			quickSortSegment (elts, pivotPosition+1, size-(pivotPosition+1-first));
		}
	}
	
	// Array sorting routine implementing Quicksort algorithm
	
	/**
	 * Destructive quick sort implementation
	 * @param elts	Array to be sorted
	 */
	public static void quickSort (int[] elts) {
		quickSortSegment (elts, 0, elts.length);
	}
	
}
