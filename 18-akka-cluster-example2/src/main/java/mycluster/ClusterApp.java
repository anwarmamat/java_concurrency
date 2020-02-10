package mycluster;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.contrib.pattern.ClusterSingletonManager;
import akka.routing.RandomPool;
import mycluster.actors.MasterActor;
import mycluster.actors.WorkerActor;

import static mycluster.messages.ConstantMessages.TOPIC_WORKERS;

import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

public class ClusterApp {

   public static void main(String[] args) {
      if (args.length > 0) {
         System.setProperty("akka.remote.netty.tcp.port", args[0]);
      }

      final ActorSystem actorSystem = ActorSystem.create("ClusterExample");

      actorSystem.actorOf(ClusterSingletonManager.defaultProps(
         Props.create(MasterActor.class),
         "master",
         PoisonPill.getInstance(),
         null
      ), "singleton");

      actorSystem.actorOf(
         new RandomPool(5).props(Props.create(WorkerActor.class)),
         TOPIC_WORKERS
      );
      
      
   }
}
