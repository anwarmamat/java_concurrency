package wordcount;

import java.io.IOException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;


public class WordCount {

	public static void main(String[] args) throws IOException {
		ActorSystem system = ActorSystem.create("testSystem");	

		ActorRef managerRef = system.actorOf(Props.create(Manager.class), "manager-actor");
		Message msg = new Message(MessageType.FOLDER,"text_files");
		managerRef.tell(msg, ActorRef.noSender());
		managerRef.tell("start", ActorRef.noSender());

		System.out.println(">>> Press ENTER to exit <<<");
		try {
			System.in.read();
		} finally {
			system.terminate();
		}

	}

}
