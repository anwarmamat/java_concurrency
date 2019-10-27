package prime;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class PrimeNumbers {

	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("calculate");

		Message msg1 = new Message(1,10);
		Message msg2 = new Message(11,20);
		Props p = Props.create(Worker.class);
		
		ActorRef actor1 = actorSystem.actorOf(p);
		ActorRef actor2 = actorSystem.actorOf(p);
		
		Future<Object> fmsg = Patterns.ask(actor1, msg1, 1000);
		Future<Object> fmsg2 = Patterns.ask(actor2, msg2, 1000);
		try {
            Timeout timeout = new Timeout(Duration.create(100, "seconds"));
			Message r1 = (Message)Await.result(fmsg, timeout.duration());
			Message r2 = (Message)Await.result(fmsg2, timeout.duration());
			System.out.println(r1.list);
			System.out.println(r2.list);
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
        	actorSystem.terminate();
        }
		

	}

}
