package unsafePublication;

/**
 * Derived from JCIP p. 51

 */
public class Holder {

	private static int n;
	
	public Holder (int n) { 
		this.n = n;
	}
	
	public void assertSanity () {
		int m = n;
		/*try {
			Thread.sleep((int)(Math.random()*100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (m != n) throw new AssertionError ("BAD CONSTRUCTION!");
	}
}
