package objectheader;

import org.openjdk.jol.layouters.CurrentLayouter;
import org.openjdk.jol.layouters.Layouter;
import org.openjdk.jol.util.VMSupport;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class Test {
	public static void main(String[]  args) {
		Object lock = new Object();
		synchronized (lock) {
		    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
		}
	}
	
}
