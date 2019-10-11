package customthreadfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class Task implements Runnable
{
   @Override
   public void run()
   {
      try
      {
      	System.out.println("Name:" + Thread.currentThread().getName());
         TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}

public class CustomThreadFactory implements ThreadFactory{

	private int counter;
	private String name;
	private List<String> stats;
	
	
	public CustomThreadFactory(String s) {
		name = s;
		counter = 1;
		stats  = new ArrayList<String>();
		
	}
	
	@Override
	public Thread newThread(Runnable runnable) {
		Thread t = new Thread(runnable, name + "Thread_" + counter);
		counter++;
		stats.add(String.format("created thread. id:  %d, name: %s, time: %s\n",  t.getId(), t.getName(), new Date()));
		return t;
	}
	public String getStats() {
		StringBuffer bf = new StringBuffer();
		for(String s:stats) {
			bf.append(s);
		}
		return bf.toString();
	}
	
	
	public static void main(String[] args) {
		CustomThreadFactory factory = new CustomThreadFactory("CustomThreadFactory");
		Task task = new Task();
		Thread t;
		for(int i = 1; i <= 10; i++) {
			t = factory.newThread(task);
			t.start();
		}

		System.out.printf("all threads are created\n");
		System.out.println("Stats:\n" + factory.getStats());
	}

}
