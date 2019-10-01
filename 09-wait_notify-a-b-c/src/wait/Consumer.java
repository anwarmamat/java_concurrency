/**
 * 	Java Wait Notify Example
 * 	A consumer requires a buffer lock, checks if the buffer empty
 *  if the buffer is empty, consumer waits
 *  if the buffer is not empty, consumer makes the buffer empty and terminates
 *  
 * @author anwar mamat
 *
 */

package wait;


public class Consumer extends Thread {
	private Buffer buffer;
	public Consumer(String name, Buffer buffer) {
		super(name);
		this.buffer = buffer;
	}
	@Override
	public void run() {
		System.out.println("Thread " + Thread.currentThread().getName() +" started");	
		try {
			take();
		} catch (Exception e) {
			System.out.println("Take failed");
		}
	}
	private void take() throws Exception {
		synchronized(buffer){
			    long allowedDuration = 11000;

			    long startTime = System.currentTimeMillis();
			    long timeLeft = allowedDuration;

				System.out.println("Thread " + Thread.currentThread().getName() +" will wait");	
				while(buffer.empty) {
					try {
						buffer.wait(5000);
						if (!buffer.empty) {
						      break; 
						}else {
						      // Check if time has expired
							long elapsed = System.currentTimeMillis() - startTime;
							timeLeft = allowedDuration - elapsed;
							if (timeLeft <= 0) throw new Exception ("Timeout");
						}

						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Thread " + Thread.currentThread().getName() +" is checking the condition");	
				}
				System.out.println("Thread " + Thread.currentThread().getName() + " resumed");	
				buffer.full = false;
				buffer.empty = true;
				System.out.println("Thread " + Thread.currentThread().getName() +" done");	
			
		}
	}
}
