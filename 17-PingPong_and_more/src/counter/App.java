package counter;



import akka.Message;
import akka.actor.*;
import akka.actor.AbstractActor.Receive;
import akka.japi.pf.ReceiveBuilder;
//import com.lightbend.akkasample.StdIn;

/**
 * counter - actor that keeps state
 */
public class App {
	
	
  static class Counter extends AbstractLoggingActor {
    // protocol
    static class Message { }


    private int counter = 0;

    @Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(Message.class, this::onMessage)
	        .build();
	}


    private void onMessage(Message message) {
      counter++;
      log().info("Increased counter " + counter);
    }

    public static Props props() {
      return Props.create(Counter.class);
    }

	
  }

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("sample1");

    final ActorRef counter = system.actorOf(Counter.props(), "counter");

    for (int i = 0; i < 5; i++) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          for (int j = 0; j < 5; j++) {
            counter.tell(new Counter.Message(), ActorRef.noSender());
          }
        }
      }).start();
    }

    

    System.out.println("ENTER to terminate");
    StdIn.readLine();
    
    
    system.terminate();
  }
}
