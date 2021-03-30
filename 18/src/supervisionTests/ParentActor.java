package supervisionTests;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;

public class ParentActor extends AbstractLoggingActor {
	static public Props parentProps = Props.create(ParentActor.class);
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, this::onMessage)
				.match(Terminated.class, this::onTerminate)
				.build();
	}
	
	public void onTerminate(Terminated msg) {	
		System.out.println("child " + msg.actor().path().name()+ " is dead.");
		System.out.println("--------------------------" + msg.getClass());
		return;
	}
	
	public void onMessage(String arg0) throws Exception {	
		ActorContext parentContext = getContext();		
		System.out.println("Parent of parent actor is " + parentContext.parent());
		ActorRef child = parentContext.actorOf(ChildActor.childProps,"test_child");
		getContext().watch(child);
		child.tell("test message", getSelf());
		//parentContext.stop(child);
		try{
			Thread.sleep(100);
		}
		catch (InterruptedException e) { }
		child.tell("test message 2", getSelf());  // Generates dead letter
	}
}
