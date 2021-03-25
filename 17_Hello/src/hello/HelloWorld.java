package hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloWorld {

	public static void main(String[] args) {
		//all actors live in this actor system
		ActorSystem system = ActorSystem.create("actors");

		Props p = Props.create(Worker.class); //Props is a ActorRef configuration object. Worker is an actor 
		ActorRef a1 = system.actorOf(p,"A1");  //reference to an actor
		ActorRef a2 = system.actorOf(p, "A2"); //reference to an actor
		
		Integer i = 100;
		a1.tell(i, null); //send a message (integer) to the actor a1
		a2.tell("Yes", a1); //send a string message to a2
		
	}

}
