/**
 * Java wait notify example
 * Consumer 1 and 2 wait for the Producer
 * Producer makes a buffer full and notifies 
 * One of the consumer acquires and lock and makes the buffer empty, then terminates
 * The other consumer acquires lock, but waits on the empty buffer.
 * 
 *  @author anwar mamat
 */
package wait;

public class WaitNotify {
	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer();
		Thread c1 = new Consumer("Consumer 1",buffer);
		Thread c2 = new Consumer("Consumer 2",buffer);
		
		Thread p1 = new Producer("Producer",buffer);
			
		c1.start();
		c2.start();
		Thread.sleep(2000); //delay the producer
		//at this point, both consumers are waiting for the producer.
		p1.start();
		
		try {
			c1.join();
			c2.join();
			p1.join();
		}catch(Exception e) {
			System.err.println("Errro");
		}
		
		System.out.println("A1 State:" + c1.getState());
		System.out.println("A2 State:" + c2.getState());
		System.out.println("B1 State:" + p1.getState());
		System.out.println("Main Done");
	}
}
