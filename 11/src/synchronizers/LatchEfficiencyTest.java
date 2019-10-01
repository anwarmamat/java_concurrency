package synchronizers;
import java.util.concurrent.CountDownLatch;


/**
 * Sample program showing performance overhead of countDown() latch operations versus
 * unsynchronized decrement.
 * 
 *
 */
public class LatchEfficiencyTest {

	public static void main(String[] args) {
		int number = 1000000; // 1 million
		CountDownLatch l = new CountDownLatch(number);
		long start;
		long end;
		
		// Compute time to do "number" # of countDown() operations.
		start = System.nanoTime();
		for (int i = 0; i < number; i++) l.countDown();
		end = System.nanoTime();
		
		System.out.printf("%,d countdowns: %,d ns.%n", number, end-start);
		
		// Compute time to do "number" # of integer decrements.
		int j = number;
		start = System.nanoTime();
		for (int i=0; i < number; i++) {j--;}
		end = System.nanoTime();
		
		System.out.printf("%,d decrements:  %,d ns.%n", number, end-start);
	}

}
