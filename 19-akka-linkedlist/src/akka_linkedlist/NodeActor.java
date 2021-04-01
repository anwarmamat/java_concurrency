package akka_linkedlist;

import akka.actor.AbstractActor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import messages.NewNodeMessage;
import messages.PrintMessage;
import messages.SumMessage;

public class NodeActor extends AbstractActor {
	
	public static Props props (Integer contents) {
		return Props.create(NodeActor.class,contents);
	}
	
	private final Integer value;	// Contents of node
	private ActorRef nextNode;		// Next node in chain
	private ActorRef parent;
	
	public NodeActor (Integer value) {
		this.value = value;
		this.parent = getContext().parent();
	}
	
	public String name() {
		return getSelf().path().name();
	}
	public String parent() {
		return parent.path().name();
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(NewNodeMessage.class, this::onNewNode)
	    		.match(PrintMessage.class, this::onPrint)
	    		.match(SumMessage.class, this::onSum)
	        .build();
	}
	
	public void onSum(SumMessage msg) {
		SumMessage new_msg;
		if("Head".equals(name())) {
			new_msg = new SumMessage(getSender(), msg.getResult() + value);
		}else {
			new_msg = new SumMessage(msg.getReplyTo(), msg.getResult() + value);
		}
		if(nextNode == null) {
			new_msg.getReplyTo().tell(new_msg.getResult(), null);
		}else {
			nextNode.tell(new_msg, getSelf());
		}
		
	}
	public void onPrint(PrintMessage msg) {
		System.out.print("->(" + parent() +"," + value + "," + name() + ")");
		if(nextNode != null) {
			nextNode.tell(msg, getSelf());
		}else {
			System.out.println("->null");
		}
	}
	
	public void onNewNode(NewNodeMessage msg){
			ActorContext context = getContext();
			if (nextNode == null) {
				nextNode = context.actorOf(NodeActor.props(msg.getValue()),"Node" + msg.getValue());
			}else {
				nextNode.tell(msg, getSelf());
			}		
	}


	

}
