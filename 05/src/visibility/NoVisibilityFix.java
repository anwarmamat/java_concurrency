package visibility;

// Properly synchronized version of "NoVisibilityAlt"

public class NoVisibilityFix {
		
		private static boolean ready;
		private static int number;
		private static Object m = new Object();
		
		private static class ReaderThread extends Thread {
			public void run () {
				boolean myReady = false;
				while (!myReady) {
					Thread.yield ();
					synchronized (m) {
						myReady = ready;
					}
				}
				System.out.println (number);
			}
		}

		public static void main(String[] args) {
			new ReaderThread().start ();
			synchronized (m) {
				number = 42;
				ready = true;
			}
		}

}
