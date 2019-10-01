package synchronizers;
import java.util.concurrent.Semaphore;


public class SemaphoreTest {

	public static void main(String[] args) {
		Semaphore s = new Semaphore(2);
		System.out.println(s);
		s.release();
		System.out.println(s);  // Do you need to acquire before release?  No!
	}

}
