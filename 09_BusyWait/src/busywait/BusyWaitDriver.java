package busywait;

public class BusyWaitDriver {

	public static void main(String[] args) {
		BoundedBuffer b = new BoundedBuffer(2);
		
		Runnable p = new Producer(b);
		Runnable c = new Consumer(b);
		
		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c);
		
		Thread t3 = new Thread(p);
		Thread t4 = new Thread(c);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		try {
			t1.join();
			t2.join();
			
			//t3.join();
			//t4.join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
