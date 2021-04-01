package akka_linkedlist;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import messages.NewNodeMessage;
import messages.PrintMessage;
import messages.SumMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class AkkaLinkedList {
	static ActorSystem actorSystem = ActorSystem.create("LinkedList");
	static void print(ActorRef head) {
		//Future<Object> fmsg = 
		Patterns.ask(head, new PrintMessage(),1000);
		try {
			return;
		} catch (Exception e) {
			System.out.println("Error in adding a new node");
		}	
	}
	static void add(ActorRef head, Integer contents) {
		NewNodeMessage req = new NewNodeMessage(contents);
		//Future<Object> fmsg = 
				Patterns.ask(head, req, 1000);
		try {
			return; 
		} catch (Exception e) {
			System.out.println("Error in adding a new node");
		}
	}
	
	static int sum(ActorRef head) {
		SumMessage req = new SumMessage(null,0);
		Future<Object> fmsg = Patterns.ask(head, req, 1000);
		try {
			Integer r = (Integer) (Await.result(fmsg, Duration.Inf()));
			return r; 
		} catch (Exception e) {
			System.out.println("Error in adding a new node");
			return 0;
		}
	}

	public static void main(String[] args) {
		ActorRef head = actorSystem.actorOf(NodeActor.props(1),"Head");
		add(head, 2);
		add(head, 3);
		add(head, 4);
		add(head, 5);
				
		print(head);
		int s = sum(head);
		try {
			Thread.sleep(100);  // Sleep long enough to ensure delivery of 
								// messages before shutting down
		} catch (InterruptedException e) {
		}
		System.out.println("Sum=" + s);
		actorSystem.terminate();

		}	
}
