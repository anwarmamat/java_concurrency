package hello;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
class PrintMyActorRefActor extends AbstractActor {
	  @Override
	  public Receive createReceive() {
	    return receiveBuilder()
	        
	        .matchEquals("printit", p -> {
	          ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
	          System.out.println("Second: " + secondRef);
	        })
	        .match(String.class, this::onString)
	        .build();
	    }
	  private void onString(String msg) {
		  System.out.println("string message");
	  }
}
public class HelloWorld {
	public static void main(String[] args) throws java.io.IOException {
	
		ActorSystem system = ActorSystem.create("testSystem");	

		ActorRef firstRef = system.actorOf(Props.create(PrintMyActorRefActor.class), "first-actor");
		System.out.println("First: " + firstRef);
		firstRef.tell("printit", ActorRef.noSender());

		System.out.println(">>> Press ENTER to exit <<<");
		try {
			System.in.read();
		} finally {
			system.terminate();
		}
	}

}
