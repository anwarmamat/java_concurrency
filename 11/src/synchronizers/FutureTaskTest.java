package synchronizers;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * Class containing main() illustrating application of FutureTask and Callable.
 *
 */
public class FutureTaskTest {

	public static void main(String[] args) {
		Callable<String> c = new Callable<String> () {
			public String call() {
				return "Callable returns result of call()!";
			}
		};
		FutureTask<String> future = new FutureTask<String>(c);
		new Thread(future).start();
		System.out.println("FutureTask launched.");
		
		System.out.println("main is doing something else now.");
		
		try {
			System.out.println(future.get());
		}
		catch (InterruptedException e) {}
		catch (ExecutionException e) {}
		finally {
			System.out.println("Done.");
		}
	}

}
