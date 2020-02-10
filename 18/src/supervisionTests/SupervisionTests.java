package supervisionTests;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class SupervisionTests {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("test_system");
		ActorRef parent = actorSystem.actorOf(ParentActor.parentProps,"test_parent");
		System.out.println("Sart main");
		parent.tell("test message", null);
		//actorSystem.terminate();  // This may throw an exception
	}

}
