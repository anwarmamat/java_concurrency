/**
 * Calculate the sum of an array using Akka actors
 *  Main creates a manager. Manager created 4 worker actors, and evenly partition the 
 *  array into 4 sections. Each actor calculates the sum of one sections, and sends
 *  the partial sum to the manager. 
 *  
 *  When manager receives all the partial sums, it calculates the total sum and displays. 
 * 
 */
		
package sum;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("system");
		final int n = 10_000_000; //number of elements. n must be a multiple of 4
		Props p = Props.create(Manager.class, n);
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
