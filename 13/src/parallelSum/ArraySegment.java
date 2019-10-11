package parallelSum;


/**
  * 
 * Class of segments of arrays.  A segment consists of a backing array, a first position, and a size.
 * If backing array is A, first position is i, and size is s, then the segment defines the subarray
 * A[i, ..., i+s-1].
 * 
 * Note: no error-checking is done on creation of segments, so e.g. A[i+s-1] may not exist.
 *
 * @param <T>
 */
public class ArraySegment<T> {
	
	private T backingArray[];	// Array of elements
	private int first;			// Position of first element in segment
	private int size;			// Number of elements in segment
	
	public ArraySegment (T backingArray[], int first, int size){
		this.backingArray = backingArray;
		this.first = first;
		this.size = size;
	}
	
	public T[] getBackingArray() {
		return backingArray;
	}
	
	public int getFirst() {
		return first;
	}
	
	public int getSize() {
		return size;
	}
}
