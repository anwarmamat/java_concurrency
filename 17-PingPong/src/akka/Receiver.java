package akka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;

public class Receiver extends AbstractLoggingActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(Message.class, this::onHello)
	        .build();
	}
	
	public void onHello(Message msg) {
		log().info("someone said " + msg.msg());
		ActorRef sender = getSender();
		sender.tell(msg, null);
		//getContext().stop(getSelf());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sender.tell(new Message("STOP"), null);
		
	}
}
