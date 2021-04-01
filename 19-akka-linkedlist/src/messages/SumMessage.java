package messages;

import akka.actor.ActorRef;

public class SumMessage {
	private ActorRef replyTo;
	private Integer result;
	
	public SumMessage (ActorRef replyTo, Integer result) {
		this.replyTo = replyTo;
		this.result = result;
	}
		
	public Integer getResult() {
		return result;
	}
	
	public ActorRef getReplyTo() {
		return replyTo;
	}
}
