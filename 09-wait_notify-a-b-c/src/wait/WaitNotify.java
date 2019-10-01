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
	public static void main(String[] args) {
		Buffer c = new Buffer();
		Thread a1 = new Consumer("Consumer 1",c);
		Thread a2 = new Consumer("Consumer 2",c);
		
		Thread b1 = new Producer("Producer",c);
			
		a1.start();
		a2.start();
		b1.start();
		
		try {
			a1.join();
			a2.join();
			b1.join();
		}catch(Exception e) {
			System.err.println("Errro");
		}
		
		System.out.println("A1 State:" + a1.getState());
		System.out.println("A2 State:" + a2.getState());
		System.out.println("B1 State:" + b1.getState());
		System.out.println("Main Done");
	}
}
