package pingpong;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;

public class PongActor extends AbstractLoggingActor {
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(String.class, this::onString)
	        .build();
	}
	
	public void onString(String msg) throws Exception {
			String payload = msg;
			if (payload.equals("stop")) { // Game over
				System.out.println(getSelf().path().name() +  ": OK");
			}
			else if (payload.equals("start")) {
				System.out.println(getSelf().path().name() +  ": Let's do it.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}				
				getSender().tell("go", getSelf());
			}
			else { // Next stroke  // "go"
				System.out.println("Pong");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				getSender().tell("go", getSelf());
			}
	}
}
