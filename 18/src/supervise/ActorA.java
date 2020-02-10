package supervise;


import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.DeathPactException;
import akka.actor.OneForOneStrategy;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.AbstractActor.Receive;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;


public class ActorA extends AbstractLoggingActor{
	ActorRef child1;
	ActorRef child2;
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Message.class, this::onMessage)
				.match(Terminated.class, this::onTerminate)
				.build();
	}
	
	
	public static final OneForOneStrategy STRATEGY = new OneForOneStrategy( // each child is treated separately
            10, // max 10 restart per 10 seconds
            Duration.create("10 seconds"),
            DeciderBuilder            		
                    .match(IllegalArgumentException.class, e -> SupervisorStrategy.resume())
                    .match(ArithmeticException.class, e -> SupervisorStrategy.restart())
                    //.match(ArithmeticException.class, e -> SupervisorStrategy.stop())
                    /*
                     * //.match(RuntimeException.class, ex -> SupervisorStrategy.resume())
	            		// .match(RuntimeException.class, ex -> SupervisorStrategy.stop())
	                    .match(ArithmeticException.class, e -> SupervisorStrategy.resume())
	                    .match(NullPointerException.class, e -> SupervisorStrategy.restart())
	                    .match(IllegalArgumentException.class, e -> SupervisorStrategy.stop())
	                    .match(RuntimeException.class, ex -> SupervisorStrategy.restart())
	                    .match(DeathPactException.class, ex ->SupervisorStrategy.resume())
	                    //.matchAny(o -> SupervisorStrategy.escalate())
	                    .matchAny(o -> SupervisorStrategy.restart())
                     */
                    .build()
    );
   
 @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY;
    }
	
	
	public void onTerminate(Terminated msg) {	
		log().info("child is dead.");
		return;
	}
	
	public void onMessage(Message msg) throws InterruptedException {		
		log().info(msg.get());
		log().info("I am {}. My parent is {}.", self().path().name(), getContext().parent().path().name());	
		
		if(msg.get().equals("start")){
			child1 = getContext().actorOf(Props.create(ActorB.class), "B");
			child2 = getContext().actorOf(Props.create(ActorC.class), "C");
			
			getContext().watch(child1);
			//getContext().watch(child2);
			
			String children = "Children of A:";
			for(ActorRef r: getContext().getChildren()) {
				children += r.path().name() + ",";
			}
			log().info(children);
			child1.tell(new Message("go"), self());
			child2.tell(new Message("go"), self());
			
			child1.tell(PoisonPill.getInstance(), self());
			
			
			child2.tell(new Message("fail"), self());
			
			
			Thread.sleep(1000);
			/**
			 *  Child1 is dead. Message will not be delivered
			 */
			child1.tell(new Message("go"), self());
		}
	}
	
	@Override
    public void preStart() throws Exception {
        log().info("Starting Actor A...");
    }
	
	@Override
    public void postStop() throws Exception {
        log().info("Stopping Actor A...");
    }


	
}
