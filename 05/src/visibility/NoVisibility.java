package visibility;
// Adapted from JCIP p. 34

public class NoVisibility {
	
	private static boolean ready;
	private static int number;
	
	private static class ReaderThread extends Thread {
		public void run () {
			while (!ready) Thread.yield ();
			System.out.println (number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start ();
		//compiler optimizes for a single thread
		number = 42;
		ready = true;
	}

}
