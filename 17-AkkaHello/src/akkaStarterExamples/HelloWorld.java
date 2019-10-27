package akkaStarterExamples;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;


public class HelloWorld {
	public static void main(String[] args) {
		
		// System, Actor names must not contain spaces
        ActorSystem actorSystem = ActorSystem.create("Message_Printer");
        Props mpProps = Props.create(MessagePrinterActor.class);
        ActorRef mpNode = actorSystem.actorOf(mpProps, "MP_Node");
        ActorRef mpNode2 = actorSystem.actorOf(mpProps, "MP_Node2");

        // Send some messages
        mpNode.tell("Hello World", null);
        mpNode.tell("akka is fab!", null);
        mpNode.tell("Is this an infinite loop?", null);
        mpNode2.tell("I'm MP_Node2!", null);
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        actorSystem.terminate();
	}
}
