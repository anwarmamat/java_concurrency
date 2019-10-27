package akkaStarterExamples;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class PingPong {

	public static void main(String[] args) {
		
		// Create actor system and actors
        ActorSystem actorSystem = ActorSystem.create("Ping_Pong");
        
        Props pingProps = Props.create(PingActor.class, 5);
        
        Props pongProps = Props.create(PongActor.class);
        
        ActorRef pingNode = actorSystem.actorOf(pingProps, "Ping_Node");
        ActorRef pongNode = actorSystem.actorOf(pongProps, "Pong_Node");
        
        // Send pongNode to pingNode, which starts systems
        Future<Object> fmsg = Patterns.ask(pingNode, pongNode, 10000);  // 5000 is time-out
        try {
            Timeout timeout = new Timeout(Duration.create(100, "seconds"));
			String response = (String)Await.result(fmsg, timeout.duration());
			System.out.println(response);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
        	actorSystem.terminate();
        }
	}

}
