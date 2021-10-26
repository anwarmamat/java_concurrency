package intChains.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import intChains.messages.NewChainRequestMessage;
import intChains.messages.NewChainResultMessage;

/**
 * Actor for creating, supervising integer chains.
 *
 */
public class ChainManagerActor extends AbstractActor {
	public static Props props = Props.create(ChainManagerActor.class);
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(NewChainRequestMessage.class, this::onNewChain)
	        .build();
	}
    
	public void onNewChain(NewChainRequestMessage msg) throws Exception {
			System.out.println("received new chain message from " + getSender().path().name());
			NewChainRequestMessage payload = msg;
			ActorContext context = getContext();
			
			ActorRef newChain = context.actorOf(IntegerNodeActor.props(payload.getContents()), "A"+payload.getContents());
			System.out.println("new actor " + newChain.path().name() + " is created");
			System.out.println(getSelf().path().name() + " sends NewChainResultMessage message to:" + getSender().path().name());
			
			/*
			 * manager send NewChainResultMessage to itself for every new integrNode Actor
			 */
			System.out.println(getSelf().path().name() + " sends result to " + getSender().path().name());
			getSender().tell(new NewChainResultMessage(newChain), getSelf());
			
	}
}
