package callableRunnable;

public class Worker1 implements Runnable{
		private int c = 0;
		public void run () {
			System.out.println("Runnable worker executing");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c = 200;
		}
		
		public int getValue() { return c;}
}
