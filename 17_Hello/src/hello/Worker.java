package hello;

import akka.actor.AbstractActor;

public class Worker extends AbstractActor{
	//AbstarctLoggingActor: same with logging 

	/**
	 * each actor has a mailbox.
	 *  if you receive a mail, you respond
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				//if you receive a mail, process it
				.match(Message.class, this::onMessage)
				.match(String.class, this::onString) //if you receive a String, process it using onString functions
				.match(Integer.class, this::onNumber)
				//.match(argument type, handler)
				.build();
	}
	
	public void onMessage(Message msg) {
		System.out.println("Received a Message");
		System.out.println(msg.getMessage());
	}
	
	public void onNumber(Integer i) {
		System.out.println("Received a number:" + i);
	}
	
	public void onString(String s) {
		String sender = getSender().path().name();
		System.out.println(getSelf().path().name() +  " Received a String:" + s + " from "  + sender );
		if(s.equals("Yes"))
			getSender().tell("No", getSelf());
	}

}
