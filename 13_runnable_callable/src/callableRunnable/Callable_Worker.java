package callableRunnable;

import java.util.concurrent.Callable;

public class Callable_Worker implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		Thread.sleep(200);
		return 100;
	}

}
