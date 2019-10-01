package immutability;

/**
 * Proper class of immutable objects
 *
 */
public class ProperImmutableAB implements ImmutableAB {
	
	public final int a;
	public final int b;
	
	ProperImmutableAB (int a, int b) {
		this.a = a;
		try { Thread.sleep(20); } catch (InterruptedException e) {}
		this.b = b;
	}
	
	public int getA() { return a; }
	public int getB() { return b; }

}
