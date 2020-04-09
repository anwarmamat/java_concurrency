package akka;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class AkkaHelloDriver extends AbstractLoggingActor{

	
//	public static Props props() {
//		return Props.create(Receiver.class);
//	}
	
	
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("actors");
		Props p = Props.create(Receiver.class);
		
		ActorRef myactor1 = system.actorOf(p);
		
		
		Props m = Props.create(AkkaHelloDriver.class);
		ActorRef myMain = system.actorOf(m);
		
		
		Message msg = new Message("hello world");
		
		myactor1.tell(msg, myMain);
		
		//myactor1.tell(akka.actor.Kill.getInstance(), ActorRef.noSender());
		//system.terminate();
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(Message.class, this::onHandle)
	        .build();
	}
	
	public void onHandle(Message msg) {
		log().info("\n received:" + msg.msg());
		if(msg.msg().equals("STOP")) {
			//do something
		}
	}
	

}
