package hello;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloWorld {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("actors");
		Props p = Props.create(Receiver.class);
		ActorRef myactor = system.actorOf(p);
		Message msg = new Message("Hello World");
		myactor.tell(msg, null);
		String s = "START";
		myactor.tell(s, null);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		system.terminate();

	}

}
