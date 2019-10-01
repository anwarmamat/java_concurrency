package busywait;

public class Consumer implements Runnable{

	private final BoundedBuffer bf;
	public Consumer(BoundedBuffer b) {
		bf = b;
	}
	@Override
	public void run() {
		while(true) {
			while(!bf.full) {
				//System.out.println("consumer...");
//				try {
//					Thread.sleep((int)(Math.random()*100));
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				//System.out.println(o);
			}
			Object o  = bf.take();
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
