package mycluster.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.dispatch.Futures;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import mycluster.messages.ConstantMessages;

public class WorkerActor extends AbstractActor implements ConstantMessages {

   private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

   private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();

   private boolean working = false;

   public WorkerActor() {
      receive(ReceiveBuilder
            .matchEquals(MSG_WORK_AVAILABLE, msg -> {
               //if (!working) {
                  sender().tell(MSG_GIVE_WORK, self());
               //}
            })
            .matchEquals(MSG_WORK, msg -> {
               final ActorRef master = sender();
               //log.info("Got work!");
               working = true;
               Futures.future(() -> {
                  //Thread.sleep(1000); // real work code goes here
                  working = false;
                  master.tell(MSG_WORK_DONE, self());
                  return null;
               }, getContext().dispatcher());
            })
            .match(Integer.class, n -> {
            	 //log.info("number:" + n);
            	 System.out.println("worker:" + self().path().name() + "\tnumber:" + n);
            	//if(n >= 10000) {
            	//	getContext().stop(self());
           	 	//}
                final ActorRef master = sender();
                //log.info("Got work!");
                working = true;
                Futures.future(() -> {
                   working = true;
                   //Thread.sleep(1000); // real work code goes here
                   boolean prime = true;
                   for(int i = 2; i < Math.sqrt(n); i++) {
                	   if( n % i == 0) {
                		   prime = false;
                		   break;
                	   }
                   }
                  
                   if(prime) {
                	   master.tell(n, self());
                   }else {
                	   master.tell(MSG_WORK_DONE, self());
                   }
                   master.tell(MSG_GIVE_WORK, self());
                   return null;
                }, getContext().dispatcher());
             })
            .match(DistributedPubSubMediator.SubscribeAck.class, msg ->
               log.info("Subscribed to 'workers'!"))
            .build()
      );
   }

   @Override
   public void preStart() {
      mediator.tell(new DistributedPubSubMediator.Subscribe(TOPIC_WORKERS, self()), self());
   }

   @Override
   public void postStop() {
      mediator.tell(new DistributedPubSubMediator.Unsubscribe(TOPIC_WORKERS, self()), self());
   }
}
