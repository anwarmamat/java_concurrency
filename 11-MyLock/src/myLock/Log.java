package myLock;

public class Log {
	public static void msg(Thread t, String m) {
		System.out.println(t.getName()+ ": " + m);
	}

	public static void msg(String m) {
		System.out.println( m);
	}
}
