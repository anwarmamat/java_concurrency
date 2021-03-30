package supervisionTests;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;

public class ChildActor extends AbstractLoggingActor {
	static public Props childProps = Props.create(ChildActor.class);
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(String.class, this::onMessage)
	        .build();
	}
	public void onMessage(String msg) {
		System.out.println("Child path (full name) is " + getSelf().path());
		System.out.println("Child name is " + getSelf().path().name());
		System.out.println("Parent name of child actor is " + getContext().parent().path().name());
		getContext().stop(getSelf());  // An actor can stop itself...
	}
	
	@Override
    public void preStart() throws Exception {
		System.out.println("Starting Child...");
    }
	
	@Override
    public void postStop() throws Exception {
		System.out.println("Stopping Child...");
    }
}
