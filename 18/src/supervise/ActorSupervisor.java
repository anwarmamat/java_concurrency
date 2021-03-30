package supervise;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class ActorSupervisor {
	public static void main(String[] args) throws InterruptedException {
		ActorSystem system = ActorSystem.create("actors");
		ActorRef actorA = system.actorOf(Props.create(ActorA.class), "A");
		actorA.tell(new Message("start"), null);
		//Thread.sleep(5000);
		//system.terminate();	
	}

}
