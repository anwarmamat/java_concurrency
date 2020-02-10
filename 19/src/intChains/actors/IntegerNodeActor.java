package intChains.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import intChains.messages.AddRequestMessage;
import intChains.messages.SumRequestMessage;
import intChains.messages.SumResultMessage;

/**
 * Class of actors that are nodes in a chain.
 * 
 *
 */
public class IntegerNodeActor extends UntypedActor {
	
	public static Props props (Integer contents) {
		return Props.create(IntegerNodeActor.class, contents);
	}
	
	private final Integer contents;	// Contents of node
	private ActorRef nextNode;		// Next node in chain
	
	public IntegerNodeActor (Integer contents) {
		this.contents = contents;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		
		if (msg instanceof AddRequestMessage) {
			
			// Add new node to end of chain
			AddRequestMessage payload = (AddRequestMessage)msg;
			
			if (nextNode == null) { // Create new node
				nextNode = getContext().actorOf(props(payload.getContents()));
			}
			else
				nextNode.tell(msg, getSelf());
			//System.out.println( getSender().path().name() + "\tsent " +payload.getContents() + " to "  +  getSelf().path().name() );
			
		}
		
		else if (msg instanceof SumRequestMessage) {
			
			// Initiate sum computation
			if (nextNode == null) {
				ActorRef replyTo = getSender();
				replyTo.tell(new SumResultMessage(replyTo, contents), getSelf());
			}
			else {
				SumResultMessage partial = new SumResultMessage(getSender(), contents);
				nextNode.tell(partial, getSelf());
			}
		}
		
		else if (msg instanceof SumResultMessage) {
			
			// Add to initiated sum computation
			SumResultMessage payload = (SumResultMessage)msg;
			SumResultMessage newMsg = new SumResultMessage(payload, contents);
			if (nextNode == null) {
				ActorRef replyTo = payload.getReplyTo();
				replyTo.tell(newMsg, getSelf());
			}
			else {
				nextNode.tell(newMsg, getSelf());
			}
		}
		else {
			System.out.printf("Error in IntegerNodeActor:  bad message %s%n", msg);
			unhandled(msg);
		}
	}

}
