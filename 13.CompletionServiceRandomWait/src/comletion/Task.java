package comletion;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<String>{

	@Override
	public String call() throws Exception {
		
		int w = (int)(Math.random() * 1000);
		TimeUnit.MILLISECONDS.sleep(w);
		return Thread.currentThread().getName() + "  \t " + w + " milliseconds.";
	}

	
}
