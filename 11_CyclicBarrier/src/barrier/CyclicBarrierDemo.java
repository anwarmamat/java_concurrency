package barrier;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
		
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier;
		cyclicBarrier = new CyclicBarrier(2, new AggregatorThread());
		
		Runnable r1 = new Worker1(cyclicBarrier);
		Runnable r2 = new Worker2(cyclicBarrier);
		Thread t1 = new Thread(r1, "t1");
		Thread t2 = new Thread(r2,"t2");
		
		
		t1.start();
		t2.start();		

	}

}
