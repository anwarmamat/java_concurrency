package example;

import java.util.concurrent.Callable;

public class Worker2 implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		
		System.out.println("Callable ");
		Thread.sleep(200);
		return 100;
	}

}
