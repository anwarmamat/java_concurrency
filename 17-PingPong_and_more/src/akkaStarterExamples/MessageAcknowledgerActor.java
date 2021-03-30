package akkaStarterExamples;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

public class MessageAcknowledgerActor extends AbstractActor {
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, this::onString)
				.build();
	}

	public void onString(String msg) {
		ActorRef sender = getSender();
		System.out.printf("Message is:  %s%n", msg);
		sender.tell(msg + " message received", getSelf());
	}

}
