package intArraySortUtils;

/**
 * Class of pairs
 * 
 * @param <T>	Type of first component in pair
 * @param <U>	Type of second component in pair
 */
public class Pair<T,U> {

	public T first;
	public U second;
	
	public Pair (T t, U u) {
		this.first = t;
		this.second = u;
	}
}
