package akka1;

import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

	
	public static void main(String[] args) {
		
		
		ActorSystem system = ActorSystem.create("system");
		Props p = Props.create(Master.class);
		
		ActorRef master = system.actorOf(p);
		
		master.tell("start", null);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		system.terminate();

	}

}
