package cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) {
		int NUM_OF_THREADS = 1_000;
		int NUM_OF_INCREMENTS = 1_000;
		ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
		CASCounter casCounter;
		try {
			casCounter = new CASCounter();
			
			IntStream.rangeClosed(0, NUM_OF_THREADS - 1)
			  .forEach(i -> service.submit(() -> IntStream
			    .rangeClosed(0, NUM_OF_INCREMENTS - 1)
			    .forEach(j -> casCounter.increment())));
			
			Thread.sleep(1000);
			System.out.println(casCounter.getCounter());
			service.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		

	}
	
}


