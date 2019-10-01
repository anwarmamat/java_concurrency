package threadStateTests;

public class ThreadStateTests {
	private static class TestThread extends Thread {
		public void run () {
			try {
				Thread.sleep (100);
			}
			catch (InterruptedException e) { }
		}
	}
	
	public static void main(String[] args) {
		TestThread t = new TestThread();
		t.start();
//		try {t.wait();} catch (InterruptedException e){}
		System.out.println("State of Thread t is " + t.getState());
		try { Thread.sleep(50); } catch (InterruptedException e) {}
		System.out.println("State of Thread t is " + t.getState());
//		try {
//			Thread.sleep (100);
//		}
//		catch (InterruptedException e) { }
		try {
			t.join();
		}
		catch (InterruptedException e) { }
		System.out.println("State of Thread t is now " + t.getState());
	}
}
