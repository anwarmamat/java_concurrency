package chat;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MessageServer {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("actors");
		Props p = Props.create(Server.class);
		ActorRef chatbot = system.actorOf(p);
		Props m = Props.create(Student.class);
		ActorRef student = system.actorOf(m);
		student.tell("READY",chatbot);
	}
}