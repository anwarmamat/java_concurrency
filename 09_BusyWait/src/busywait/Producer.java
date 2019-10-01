package busywait;

public class Producer implements Runnable{

	private final BoundedBuffer bf;
	public Producer(BoundedBuffer b) {
		bf = b;
	}
	@Override
	public void run() {
		while(true) {
			while(bf.full) {
//				System.out.println("producer...");
//				try {
//					Thread.sleep((int)(Math.random()*100));
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				//System.out.println(i);
			}
			Integer i = (int)(Math.random() * 100);
			bf.put(i);
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
