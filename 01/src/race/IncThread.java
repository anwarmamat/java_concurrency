//package race;

/**
 * Class of threads that increment a shared static field.
 */
public class IncThread implements Runnable {
	
	private static int shared = 0;	// Shared static field
	private String name = "";		// Name of thread

	/**
	 * @param name	Name to assign to thread
	 */
	IncThread (String name) {
		this.name = name;
	}
	
	/**
	 * @return	value of "shared"
	 */
	public static int getShared() {
		return shared;
	}

	/**
	 * Reset shared variable to 0
	 */
	public static void resetShared() {
		shared = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * run method reads shared variable into private variable, prints
	 * value, increments private variable, then writes back.
	 */
	public void run () {
		int myShared = shared;
		System.out.println (name + " read shared = " + myShared);
//		try {
//			Thread.sleep (100);
//		}
//		catch (InterruptedException e) { }
		myShared++;
		shared = myShared;
		System.out.println (name + " assigned to shared: " + myShared);
	}
}
