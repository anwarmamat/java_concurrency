package barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker2 implements Runnable{
	private CyclicBarrier cyclicBarrier;
	public Worker2(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println(Thread.currentThread().getName() + "\t working");
				Thread.sleep(1000);
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
