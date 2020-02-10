package supervise;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;


public class ActorB extends AbstractLoggingActor{
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Message.class, this::onMessage)
				.build();
	}
	
	
	public void onMessage(Message msg) {		
		log().info("I am {}. My parent is {}.", self().path().name(), getContext().parent().path().name());
		//getContext().stop(self());
		
	}
	
	@Override
    public void preStart() throws Exception {
        log().info("Starting Actor B...");
    }
	
	@Override
    public void postStop() throws Exception {
        log().info("Stopping Actor B...");
    }


	
}
