package barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker1 implements Runnable{
	private CyclicBarrier cyclicBarrier;
	public Worker1(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println(Thread.currentThread().getName() + "\t working");
				Thread.sleep(4000);
				System.out.println(Thread.currentThread().getName() + "\t done");
				cyclicBarrier.await();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
