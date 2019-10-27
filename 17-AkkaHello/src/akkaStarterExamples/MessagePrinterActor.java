package akkaStarterExamples;

import akka.actor.AbstractActor;


public class MessagePrinterActor extends AbstractActor {

//	@Override
//	public void onReceive(Object msg) throws Exception {
//		if (msg instanceof String) {
//			System.out.printf("Message is: %s%n", msg);
//		}
//	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(String.class, this::onMessage)
	        .build();
	}
	
	public void onMessage(String msg) {
		System.out.printf("Message is: %s%n", msg);
	}
}
