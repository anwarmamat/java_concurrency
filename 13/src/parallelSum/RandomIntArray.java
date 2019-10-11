package parallelSum;

/**
 * Constructs and returns random arrays of integers.
 * 
 *
 */
public class RandomIntArray {
	
	/**
	 * Return integer array of size elements, where each element is in the range [-bound ... bound] inclusive.
	 * 
	 * @param size	# of elements constructed array should have
	 * @param bound range of elements in the array [-bound ... bound]
	 * @return		array with size # of elements, each element in the range [-bound ... bound]
	 */
	public static Integer[] getRandomIntArray (int size, int bound) {
		Integer[] a = new Integer[size];
		SymmetricBoundedRandomInt r = new SymmetricBoundedRandomInt(bound);
		for (int i=0; i < size; i++) {
			a[i] = r.next();
		}
		return a;
	}
}
