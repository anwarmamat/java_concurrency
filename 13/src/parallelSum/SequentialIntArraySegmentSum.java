package parallelSum;

/**
 * 
 * Holder for method that computes sum of an array segment of integers.
 *
 */

public class SequentialIntArraySegmentSum {
	
	/**
	 * Precondition:  segment is a valid array segment, i.e. all element in segment exist
	 * Postcondition: return sum of elements in segment
	 * Exception:  Raises ArrayIndexOutOfBoundsException if segment is not valid
	 */
	
	public static int sum (ArraySegment<Integer> segment) {
		Integer[] backingArray = segment.getBackingArray();
		int first = segment.getFirst();
		int size = segment.getSize();
		int result=0;
		for (int i=first; i < first+size; i++) result += backingArray[i];
		return (result);
	}
}
