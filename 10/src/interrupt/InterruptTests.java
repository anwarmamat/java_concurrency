package interrupt;

public class InterruptTests {
	
	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run () {
				//Thread.currentThread().interrupt();
				while (true) { 
					//if(Thread.interrupted()) break;
					//if(Thread.currentThread().isInterrupted()) break;
				} 
			}
		};
		
		Thread t1 = new Thread() {
			public void run () {
				try {
					Thread.sleep(10000);
				}
				catch (InterruptedException e) {
					System.out.println ("Thread t1 interrupted.");
				}
			}
		};
		
		System.out.println("Starting...");
		t.start();
		try {
			Thread.sleep(10);
		}
		catch (InterruptedException e) { }
		
		System.out.println("Thread t state = " + t.getState());     
		System.out.println("Thread t interrupted status = " + t.isInterrupted());
		t.interrupt();
		// This shows that daemon threads, when interrupted while sleeping,
		// also generate InterruptedExceptions.
		
		//t1.setDaemon(true);
		t1.start();
		System.out.println("Thread t1 first state = " + t1.getState());
		try {
			Thread.sleep(50);
		}
		catch (InterruptedException e) { }
		t1.interrupt();
		
		
		System.out.println("Thread t1 next state = " + t1.getState());
	}
}
