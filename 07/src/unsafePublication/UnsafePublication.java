package unsafePublication;

/**
 * An attempt to cause a Holder object to trip up on assertSanity.  I have not been
 * able to get this to work, however; compiler optimizations may be the issue.
 * 
 *
 */
public class UnsafePublication {

	private static Holder h = new Holder(42);
	public static class ThreadCheck extends Thread {
		public void run () {
			while (true) {
				h.assertSanity();
			}
		}
	}
	
	public static void main(String[] args) {
		Thread t = new ThreadCheck();
		//t.setDaemon(true);
		t.start();
		System.out.println ("Thread started");
		
		for (long i = 0L; i < 10000L; i++) { 
				h = new Holder(42);
		}
		System.out.println ("Objects created");

	}
	

}
