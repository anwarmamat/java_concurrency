package executor;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Worker1 implements Callable {
	private static AtomicInteger c1 = new AtomicInteger();
	@Override
	public String call() {
		String name = Thread.currentThread().getName();
		
		for(int i = 0; i < 5; i++) {
			System.out.println(name + "  working\t count=" + c1.incrementAndGet());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(name + "\t interrupted.");
			}
		}
		return "done!";
	}

}
