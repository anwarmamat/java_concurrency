package intChains.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import intChains.messages.AddRequestMessage;
import intChains.messages.NewChainRequestMessage;
import intChains.messages.SumRequestMessage;
import intChains.messages.SumResultMessage;

/**
 * Class of actors that are nodes in a chain.
 */
public class IntegerNodeActor extends AbstractActor {

	public static Props props (Integer contents) {
		return Props.create(IntegerNodeActor.class,contents);
	}

	private final Integer contents;	// Contents of node
	private ActorRef nextNode;		// Next node in chain

	public IntegerNodeActor (Integer contents) {
		this.contents = contents;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(AddRequestMessage.class, this::onAddRequest)
				.match(SumRequestMessage.class, this::onSumRequest)
				.match(SumResultMessage.class, this::onSumResult)
				.build();
	}

	public void onAddRequest(AddRequestMessage msg) throws Exception {
		// Add new node to end of chain
		AddRequestMessage payload = msg;

		if (nextNode == null) { // Create new node
			nextNode = getContext().actorOf(props(payload.getContents()),"A"+payload.getContents());
			System.out.println(nextNode.path() + " is created.");
		}
		else
			nextNode.tell(msg, getSelf());
		//System.out.println( getSender().path().name() + "\tsent " +payload.getContents() + " to "  +  getSelf().path().name() );

	}
	public void onSumRequest(SumRequestMessage msg) throws Exception {
		System.out.println(self().path().name()+ " received msg from " + getSender().path().name()  );
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
	public void onSumResult(SumResultMessage msg) throws Exception {
		// Add to initiated sum computation
		System.out.println("current result: " + msg.getResult());
		SumResultMessage payload = msg;
		SumResultMessage newMsg = new SumResultMessage(payload, contents);
		if (nextNode == null) {
			ActorRef replyTo = payload.getReplyTo();
			replyTo.tell(newMsg, getSelf());
		}
		else {
			nextNode.tell(newMsg, getSelf());

		}
	}
}
