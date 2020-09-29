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
		System.out.println(Thread.currentThread().getName() +" started");	
		try {
			take();
		} catch (Exception e) {
			System.out.println("Take failed");
		}
	}
	private void take() throws Exception {
		System.out.println(Thread.currentThread().getName() +" want to consume");
		synchronized(buffer){
				System.out.println(Thread.currentThread().getName() +" acquired the lock.");	
			    long allowedDuration = 10000;
			    long startTime = System.currentTimeMillis();
			    long timeLeft = allowedDuration;
			    
				while(buffer.empty) {
					try {
						System.out.println(Thread.currentThread().getName() +" will wait");	
						System.out.println(Thread.currentThread().getName() +" released the lock");	
						sleep(1000);
						buffer.wait(1000);
						//---->
						//when the consumer wakes up, it continues from here
						
						System.out.println(Thread.currentThread().getName() +" woke up");	
						
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
					System.out.println(Thread.currentThread().getName() +" is checking the condition");	
				}
				System.out.println(Thread.currentThread().getName() + " resumed");	
				buffer.full = false;
				buffer.empty = true;
				System.out.println(Thread.currentThread().getName() +" done");	
				buffer.notify();//notify the other consumer
		}
	}
}
