package supervise;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.AbstractActor.Receive;
import akka.japi.pf.ReceiveBuilder;
public class ActorC extends AbstractLoggingActor{
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Message.class, this::onMessage)
				.build();
	}
		
	public void onMessage(Message msg) {		
		log().info("I am {}. My parent is {}.", self().path().name(), getContext().parent().path().name());
		
		if(msg.get().equals("fail")) {
       	 System.out.println("I am failing.");
       	  throw new IllegalArgumentException("Woops, something went wrong");   	 
		  //throw new ArithmeticException("error.");
       	  //String s = null;
       	  //int l = s.length(); //thros NullPointerException. Supervisor restarts the actor
		}
		/**
		 *   Supervisor Strategy 
		 * 	.match(ArithmeticException.class, e -> SupervisorStrategy.restart())
		 * 
		 *  after the error, this child will be restarted
		 */
		
		//throw new RuntimeException("Woops, something went wrong");
   	 	//throw new ArithmeticException("Woops, something went wrong");
   	 	//throw new IllegalArgumentException("Woops, something went wrong");
   	 	//throw new NoSuchElementException();
	}
	
	@Override
    public void preStart() throws Exception {
        log().info("Starting Actor C...");
    }
	
	@Override
    public void postStop() throws Exception {
        log().info("Stopping Actor C...");
    }
	
}
