package immutability;

public class ProperImmutableABDriver {

	public static void main(String[] args) {
		ProperImmutableAB ab = new ProperImmutableAB (2,3);
		new ThreadABPrinter(ab).start();
		try { Thread.sleep(20); } catch (InterruptedException e) { }
		System.out.println ("main sees ab = " + ab);

	}

}
