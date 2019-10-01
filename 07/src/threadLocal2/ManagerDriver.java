package threadLocal2;

public class ManagerDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable r  = new ManagerThread ("t1", 10);
		Thread t1 = new Thread (r);
		Thread t2 = new Thread (r);
		t1.start();
		t2.start();
	}

}
