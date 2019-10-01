package interrupt;

public class InterruptTest {

	public static void main(String[] args)  {
		Thread t1 = new T1();
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1.interrupt();
		
		System.out.println("Main done");

	}

}
