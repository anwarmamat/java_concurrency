package comletion;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String>{

	@Override
	public String call() throws Exception {
		
		TimeUnit.SECONDS.sleep(3);
		return Thread.currentThread().getName();
	}

	
}
