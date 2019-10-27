package akkaStarterExamples;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class MessageAcknowledgerActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			ActorRef sender = getSender();
			String payload = (String)msg;
			System.out.printf("Message is:  %s%n", payload);
			sender.tell(payload + " message received", getSelf());
		}
	}

}
