package synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker extends Thread{
	private CyclicBarrier entryBarrier ;
	private CyclicBarrier exitBarrier ;
	Worker(CyclicBarrier entryBarrier, CyclicBarrier exitBarrier  ){
		this.entryBarrier = entryBarrier;
		this.exitBarrier = exitBarrier;
	}
	public void run() {
		int duration = (int)(Math.random() * 1000);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" Ready");
		
		try {
			entryBarrier.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BrokenBarrierException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		duration = (int)(Math.random() * 1000);
		try {
			
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+" Finished");
		
		try {
			exitBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}

public class CyclicBarrierExample {
	public static void main(String[] args)  {
		int numOfTasks = 5;
		CyclicBarrier entryBarrier = new CyclicBarrier(numOfTasks + 1);
		CyclicBarrier exitBarrier = new  CyclicBarrier(numOfTasks + 1);
		Thread[] workers = new Thread[numOfTasks];
		for(int i = 0; i < numOfTasks; i++) {
			workers[i] = new Worker(entryBarrier,exitBarrier);
			workers[i].start();
		}
		System.out.println("Waiting for Workers:");
		try {
			entryBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Workers working");
		
		try {
			exitBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
	}

}
