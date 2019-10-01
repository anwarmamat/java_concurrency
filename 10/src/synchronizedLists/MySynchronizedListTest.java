package synchronizedLists;
import java.util.List;


public class MySynchronizedListTest {

	public static <T> MiniList<T> synchronizedList (List<T> l) {
		return new SynchronizedList<T>(l);
	}


}
