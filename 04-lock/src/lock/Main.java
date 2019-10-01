package lock;

public class Main {

	public static void main(String[] args) {
		Counter c = new Counter();
		Runnable r1 = new Worker(c);
		Runnable r2 = new Worker(c);
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int r = c.get();
		System.out.println("r=" + r);
	}

}
