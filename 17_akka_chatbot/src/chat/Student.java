package chat;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.AbstractActor.Receive;

public class Student extends AbstractLoggingActor{
	private ActorRef chatbot; 
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(String.class, this::onMsg)	
	        .build();
	}
	
	public void onMsg(String msg) {
		if(msg.equals("READY")){
			log().info("student received:  READY" );
			chatbot = getSender();
			send();
			chatbot.tell(new GoodBye(),null);
		}
	}
	
	private void send() {
		chatbot.tell(new Join(1,"Alice"),getSelf());
		chatbot.tell(new Message(1,"P1"),getSelf());
		chatbot.tell(new Message(1,"P2"),getSelf());
		chatbot.tell(new Join(2,"Bob"),getSelf());
		chatbot.tell(new Message(2,"P1"),getSelf());
		chatbot.tell(new Message(2,"P2"),getSelf());
		chatbot.tell(new Message(3,"P1"),getSelf());
		chatbot.tell(new Message(2,"P3"),getSelf());
	}

}
