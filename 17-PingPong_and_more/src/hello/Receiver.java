package hello;

import akka.actor.AbstractLoggingActor;

public class Receiver extends AbstractLoggingActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Message.class, this::onMessage)
				.match(String.class, this::onString)
				.build();
	}
	
	public void onMessage(Message msg) {
		log().info(msg.msg());
	}
	
	public void onString(String msg) {
		log().info(msg);
	}

}
