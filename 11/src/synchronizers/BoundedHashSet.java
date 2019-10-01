package synchronizers;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Adapted from JCIP p. 100.  Uses semaphore to bound size of HashSet.
 
 *
 * @param <T>	Type of elements in set
 */
public class BoundedHashSet<T> {

	private final Set<T> set;
	private final Semaphore spaceAvailable;
	
	public BoundedHashSet (int capacity) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		spaceAvailable = new Semaphore(capacity);
	}
	
	public boolean add(T o) throws InterruptedException {
		spaceAvailable.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return(wasAdded);
		}
		finally {
			if (!wasAdded) spaceAvailable.release();
		}
	}
	
	public boolean remove(T o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved) spaceAvailable.release();
		return wasRemoved;
	}

}
