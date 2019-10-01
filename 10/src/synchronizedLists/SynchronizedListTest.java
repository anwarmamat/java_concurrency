package synchronizedLists;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class SynchronizedListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> l = new ArrayList<Integer> ();
		l.add(2);
		System.out.println("l = " + l);
		List<Integer> syncL1 = Collections.synchronizedList(l);
		l.add(3);
		List<Integer> syncL2 = Collections.synchronizedList(l);
		System.out.println("syncL1 = " + syncL1);
		System.out.println("syncL2 = " + syncL2);
		System.out.println("l = " + l);
	}

}
