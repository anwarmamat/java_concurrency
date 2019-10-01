package instanceConfinement;
import java.util.HashSet;
import java.util.Set;

/**
 * Example of instance confinement, derived from JCIP p. 59.
 * SyncIntegerHashSet is thread-safe, even though it uses a non-thread-safe object
 * inside (HashSet).
 *
 */
public class SyncIntegerHashSet {
	
	// Invariant:  an integer i is in the set if and only if it was added to the set.
	
	private final Set<Integer> mySet = new HashSet<Integer>();
	
	/**
	 * Adds an integer to the set
	 * 
	 * Precondition:  none
	 * Postcondition:  i is an element of the set, which is otherwise unchanged
	 * ExceptioN:  none
	 * 
	 * @param i	Integer to add to the set
	 */
	public synchronized void addInteger(Integer i) { mySet.add(i); }

	/**
	 * Returns boolean reflecting whether or not argument is in set.
	 * 
	 * Precondition:  none
	 * Postcondition:  returns true if and only if i is in the set
	 * Exception:  none
	 * 
	 * @param i
	 * @return
	 */
	public synchronized boolean containsInteger(Integer i) {
		return mySet.contains(i);
	}
}
