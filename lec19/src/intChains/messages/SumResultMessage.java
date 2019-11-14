package intChains.messages;

import akka.actor.ActorRef;

public class SumResultMessage {
	private ActorRef replyTo;
	private Integer result;
	
	public SumResultMessage (ActorRef replyTo, Integer result) {
		this.replyTo = replyTo;
		this.result = result;
	}
	
	public SumResultMessage (SumResultMessage oldMsg, Integer summand) {
		this.replyTo = oldMsg.replyTo;
		this.result = oldMsg.result + summand;
	}
	
	public Integer getResult() {
		return result;
	}
	
	public ActorRef getReplyTo() {
		return replyTo;
	}
}
