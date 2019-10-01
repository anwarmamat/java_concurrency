package event;
import java.util.concurrent.*;

public class Taker extends Thread {
  private final BlockingQueue<Event> listeners = new LinkedBlockingQueue<Event>();

  public void run() {
      while (true) {
	  try {	  
	
			  listeners.take().go();	//wait if queue is empty, block yourself
		  
	  } catch (InterruptedException e) {
	      break;
	  }
	  }
  }

  
  public void insert(Event a) {
    listeners.add(a);
  }
}
