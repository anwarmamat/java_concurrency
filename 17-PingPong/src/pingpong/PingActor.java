package pingpong;

import akka.actor.ActorRef;
import akka.actor.AbstractLoggingActor;

public class PingActor extends AbstractLoggingActor {
	
	private int numHitsLeft;
	private ActorRef partner;
	private ActorRef referee;  // Actor to notify when done.
	
	public PingActor(int numHits) {
		this.numHitsLeft = numHits;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(ActorRef.class, this::onActorRef)
	    		.match(String.class, this::onString)
	        .build();
	}

	// 1st time, mail sends message 
	public void onActorRef(ActorRef msg) {
		partner = msg;
		referee = getSender();  // Originator of message
		System.out.println(getSelf().path().name() +  ": Game on!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		partner.tell("start", getSelf());
	}
	public void onString(String msg) {
		if (numHitsLeft == 0) { // stop game
			System.out.println(getSelf().path().name() +  ": Game over");
			partner.tell("stop", getSelf());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			referee.tell("stop", getSelf());
		}
		else { // keep playing 
			System.out.print("Ping ... " + numHitsLeft +  "\t");
			partner.tell("go", getSelf());
			numHitsLeft--;
		}
	}
	
	
}
