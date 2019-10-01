package lock;

public class Worker implements Runnable {
	static Object l = new Object();
	Counter c;
	public Worker(Counter c) {
		this.c = c;
	}
	@Override
	public void run() {
		
		for(int i = 0; i < 1000; i++) {
			synchronized (l) {//DOES NOT WORK
				c.inc();//simple 
			}
		}
		
	}

}
