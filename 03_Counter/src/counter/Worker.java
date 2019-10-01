package counter;

public class Worker implements Runnable{
	private static Object lock = new Object();
	private int limit;
	private Counter t;
	Worker(Counter c, int n){
		t = c;
		limit = n;
	}
	
	@Override
	public void run() {
		
		for(int i=0; i < limit; i++){
				t.inc();//atomic
		}
		
	}


}
