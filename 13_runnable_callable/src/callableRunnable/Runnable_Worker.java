package callableRunnable;

public class Runnable_Worker implements Runnable{
		private int c = 0;
		public void run () {
			System.out.println("Runnable worker executing");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			c = 200;
		}
		
		public int getValue() { return c;}
}
