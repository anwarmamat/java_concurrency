package intChains.actors;

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
public class ChainManagerActor extends UntypedActor {
	
	public static Props props = Props.create(ChainManagerActor.class);

	@Override
	public void onReceive(Object msg) throws Exception {
		//System.out.println("Manager received");
		if (msg instanceof NewChainRequestMessage) {
			NewChainRequestMessage payload = (NewChainRequestMessage)msg;
			ActorContext context = getContext();
			
			ActorRef newChain = context.actorOf(IntegerNodeActor.props(payload.getContents()));
			System.out.println("new actor " + newChain.path().name() + " is created");
			System.out.println(getSelf().path().name() + " sends NewChainResultMessage message to:" + getSender().path().name());
			
			
			/*
			 * manager send NewChainResultMessage to itself for every new integrNode Actor
			 */
			getSender().tell(new NewChainResultMessage(newChain), getSelf());
			
		}
		else {
			unhandled(msg);
		}
	}

}
