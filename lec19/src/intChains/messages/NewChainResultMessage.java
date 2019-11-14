package intChains.messages;

import akka.actor.ActorRef;

public class NewChainResultMessage {
	private final ActorRef node;
	
	public NewChainResultMessage(ActorRef node) {
		this.node = node;
	}
	
	public ActorRef getIntegerNode() {
		return node;
	}
}
