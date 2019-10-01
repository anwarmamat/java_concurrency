package immutability;

public class ThreadABPrinter extends Thread {
	
	private ImmutableAB ab;

	ThreadABPrinter (ImmutableAB ab) {
		this.ab = ab;
	}
	
	public void run () {
		System.out.printf ("Thread sees b = %d%n", ab.getB());
		try { Thread.sleep (100); } catch (InterruptedException e) {}
		System.out.printf ("Thread sees b = %d%n", ab.getB());
	}

}
