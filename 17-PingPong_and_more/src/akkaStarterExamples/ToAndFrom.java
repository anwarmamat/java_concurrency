package akkaStarterExamples;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class ToAndFrom {

	public static void main(String[] args) {
		
		// System, Actor names must not contain spaces
        ActorSystem actorSystem = ActorSystem.create("Message_Responder");
        Props maProps = Props.create(MessageAcknowledgerActor.class);
        ActorRef maNode = actorSystem.actorOf(maProps);

        Future<Object> f = Patterns.ask(maNode, "Testing", 5000); // Timeout is 5000 millis

        try {
            Timeout timeout = new Timeout(Duration.create(100, "seconds"));
			String response = (String)Await.result(f, timeout.duration());
	        System.out.printf("Response is:  %s", response);
	       
		}
        catch (Exception e) {
			e.printStackTrace();
		}
        finally {
        	actorSystem.terminate();
        }
	}

}
