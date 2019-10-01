package immutability;
// 

/**
 * Immutable-seeming class whose objects publish "this" implicitly by
 * launching a thread in the constructor
 * 
 *
 */
public class ImproperImmutableAB implements ImmutableAB {
	
	public final int a;
	public final int b;

	ImproperImmutableAB (int a, int b) {
		this.a = a;
		new ThreadABPrinter (this).start();
		try { Thread.sleep(20); } catch (InterruptedException e) {}
		this.b = b;
	}
	
	public int getA () { return a; }
	public int getB () { return b; }

}
