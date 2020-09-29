
package wait;

public class Producer extends Thread {
	private Buffer buffer;
	public Producer(String name, Buffer c) {
		super(name);
		this.buffer = c;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " started");
		try {
			sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		put();
	}
	private void put() {
		synchronized(buffer){
			while(buffer.full) {
				try {
					buffer.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() +" will notify");	
			buffer.full = true;
			buffer.empty = false;
			buffer.notify();
			System.out.println(Thread.currentThread().getName() + " notified others");	
		}
	}
}
