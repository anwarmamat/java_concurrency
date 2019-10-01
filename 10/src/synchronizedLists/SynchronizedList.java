package synchronizedLists;
import java.util.List;


public class SynchronizedList<T> implements MiniList<T>
// implements List<T> 
{
	
	final List<T> backingList;
	
	SynchronizedList (List<T> list) {
		this.backingList = list;
	}
	
	public synchronized int size () {
		return backingList.size();
	}

}
