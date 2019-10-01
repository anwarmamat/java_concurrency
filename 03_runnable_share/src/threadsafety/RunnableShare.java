package threadsafety;


public class RunnableShare implements Runnable{
	private int count = 0;
	private static int copies = 0;
	@Override 
	public void run() {
		count++;
		copies++;
	}

	public int getCount() {
		return count;
	}
	
	public int getCopies() {
		return copies;
	}
}
