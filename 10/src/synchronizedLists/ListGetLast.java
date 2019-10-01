package synchronizedLists;
import java.util.List;


/**
 * Static method for retrieving and removing last element in a list.
 * 
 */
public class ListGetLast {

	public static Object getLast (List<Object> l) {
		synchronized (l) { 
			int lastIndex = l.size() - 1;
			return (l.get(lastIndex));
		}
	}

}
