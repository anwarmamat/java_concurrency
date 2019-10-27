package prime;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;

public class Worker extends AbstractLoggingActor{

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(Message.class, this::onCompute)
	        .build();
	}
	public void onCompute(Message msg) {
		int s = msg.lo;
		int e = msg.hi;
		for(int i = s; i <= e; i++) {
			if(i % 2 == 0) msg.list.add(i);
		}
		getSender().tell(msg,getSelf());
	}

}
