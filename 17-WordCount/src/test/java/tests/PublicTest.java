package tests;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import wordcount.Manager;
import wordcount.Message;
import wordcount.MessageType;

public class PublicTest {
	private ActorSystem system;
	ActorRef managerRef;
	

	@Before
	public void setUp() throws Exception {
		system = ActorSystem.create("testSystem");	
		managerRef = system.actorOf(Props.create(Manager.class), "manager-actor");
	}

	@After
	public void tearDown() throws Exception {
		system.terminate();
	}

	@Test
	public void test() {
		Message msg = new Message(MessageType.FOLDER,"test_files");
		managerRef.tell(msg, ActorRef.noSender());
		managerRef.tell("start", ActorRef.noSender());
	}

}
