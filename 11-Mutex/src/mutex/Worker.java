package mutex;

public class Worker implements Runnable{
	private static Object lock = new Object();
	private int limit;
	private Counter t;
	Worker(Counter c, int limit){
		t = c;
		this.limit = limit;
	}
	
	@Override
	public void run() {
		for(int i=0; i < limit; i++){
				t.inc();
		}
		
	}


}
