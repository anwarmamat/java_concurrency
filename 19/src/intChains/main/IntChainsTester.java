package intChains.main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import intChains.actors.ChainManagerActor;
import intChains.messages.AddRequestMessage;
import intChains.messages.NewChainRequestMessage;
import intChains.messages.NewChainResultMessage;
import intChains.messages.SumRequestMessage;
import intChains.messages.SumResultMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Code for testing IntChains actors.
 * 
 *
 */
public class IntChainsTester {

	static ActorSystem actorSystem = ActorSystem.create("IntChainsSystem");
	
	static ActorRef creator = actorSystem.actorOf(ChainManagerActor.props,"manager");

	
	
	/**
	 * Method for implementing Java get()-like behavior for Scala futures
	 * @param f
	 * @return
	 */
	static Object get(Future<Object> f) {
		try {
			Object obj = Await.result(f, Duration.Inf());
			return obj;
		} catch (Exception e) {
			System.out.println("Error in get()");
			return null;
		}
	}

	/**
	 * Create a single-node chain storing the given integer.
	 * 
	 * @param contents	Data to store in node
	 * @return			Reference to the newly created node
	 */
	static ActorRef makeChain(Integer contents) {
		
		NewChainRequestMessage req = new NewChainRequestMessage(contents);
		
		
		Future<Object> fmsg = Patterns.ask(creator, req, 1000);
		
		try {
			NewChainResultMessage msg = (NewChainResultMessage) get(fmsg);
			return (msg.getIntegerNode());
		} catch (Exception e) {
			System.out.println("Error in makeChain()");
			return null;
		}
	}

	/**
	 * Add a new node to the end of the given chain, holding the given integer.
	 * 
	 * @param chain
	 * @param contents
	 */
	static void add(ActorRef chain, Integer contents) {
		AddRequestMessage req = new AddRequestMessage(contents);
		Patterns.ask(chain, req, 1000); // Fire and forget
	}

	/**
	 * Compute the sum of the given chain.
	 * 
	 * @param chain
	 * @return
	 */
	static Integer sum(ActorRef chain) {
		SumRequestMessage req = new SumRequestMessage();
		Future<Object> fmsg = Patterns.ask(chain, req, 1000);
		try {
			SumResultMessage msg = (SumResultMessage) get(fmsg);
			return (msg.getResult());
		} catch (Exception e) {
			System.out.println("Error in add()");
			return null;
		}
	}

	public static void main(String[] args) {
		
		
		System.out.println("manager actor " + creator.path().name() + " is created");
		
		ActorRef chain = makeChain(0);
		
		
		add(chain, 1);
		
		add(chain, 2);
		add(chain, 3);
		add(chain, 42);
		System.out.printf("Sum of chain is %d%n", sum(chain));

		Thread test1 = new Thread() {
			public void run() {
				System.out.printf("Sum of chain in test1 is %d%n", sum(chain));
			}
		};

		Thread test2 = new Thread() {
			public void run() {
				System.out.printf("Sum of chain in test2 is %d%n", sum(chain));
			}
		};

		test1.start();
		test2.start();

		try {
			Thread.sleep(100);  // Sleep long enough to ensure delivery of
								// messages before shutting down
		} catch (InterruptedException e) {
		}
		;
		actorSystem.terminate();

	}
}
