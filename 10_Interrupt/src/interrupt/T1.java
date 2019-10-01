package interrupt;

public class T1 extends Thread{
	private void foo() throws InterruptedException {
		synchronized(this) {
			try {
				wait();
				//sleep();
			} catch (InterruptedException e) {
				System.out.println("T1 foo interrupted");
				Thread.currentThread().interrupt(); // Here!
				//throw e;
			}
		}
	}
	public void run() {
		while(true) {
			System.out.println("T1 running");
			try {
				foo();
			} catch (InterruptedException e) {
				System.out.println("T1 interrupted");
				if(isInterrupted()) break;
			}
		}
	}
}
