package stateDependent;
public class BoundedBufferWaitNoNullDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final BoundedBufferWaitNoNull buffer = new BoundedBufferWaitNoNull(1);

		Thread reader = new Thread() {
			public void run () {
				System.out.println("Reader starts");
				try { buffer.take(); }
				catch (InterruptedException e) {}
				System.out.println("Reader finished");
			}
		};

		Thread writer = new Thread() {
			public void run () {
				System.out.println("Writer starts");
				try { buffer.put(new Object ()); }
				catch (InterruptedException e) {}
				System.out.println("Writer finished");
			}
		};
		reader.start();
		try { Thread.sleep (50); } catch (InterruptedException e) {};
		writer.start();
		try { reader.join(); } catch (InterruptedException e) {}
		try { writer.join(); } catch (InterruptedException e) {}
		System.out.println ("Main finished");
	}

}
