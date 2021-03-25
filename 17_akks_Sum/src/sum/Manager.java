package sum;

import java.util.Random;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Manager extends AbstractLoggingActor{
	private int NUMELTS = 20;   		// Number of elements in array (1,000,000)
	private int NUMCPUS;
	private int NUMACTORS; 
	private double[] input;
	private double total = 0;
	private int taskDone = 0; 
	private long duration;
	public Manager(int n) {
		NUMELTS = n;
		input = createArray(NUMELTS);
		NUMCPUS  = Runtime.getRuntime().availableProcessors();
		NUMACTORS  = 4;
		
	}
	private double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = i+1; //rand.nextInt(100);
        }
        return input;
    }

	@Override
	public Receive createReceive() {
		return receiveBuilder().
				match(String.class, this::onCompute).
				match(Double.class, this::onSum).
				build();
		
	}

	public void onSum(Double d) {
		total += d;
		taskDone++;  //number of workers finished
		if(taskDone == NUMACTORS ){
			duration = System.nanoTime() - duration;
			System.out.println("Total:\t" + total);
			System.out.println("Duration:" + duration / 1000);
			
			}	
	}
	public void onCompute(String s) {
		if(s.equals("start")){
			System.out.println("Start");
			duration = System.nanoTime();
		}

		ActorRef[] actors = new ActorRef[NUMACTORS];
		
		Props p = Props.create(Worker.class);
		//creates actors
		for(int i = 0; i < NUMACTORS; i++) {
			actors[i] = getContext().system().actorOf(p, "A"+i);
		}
		//send the task to the actors
		for(int i = 0; i < NUMACTORS; i++) {
			Task task = new Task (input,   NUMELTS / NUMACTORS * i,  NUMELTS / NUMACTORS * (i + 1));
			actors[i].tell(task, getSelf());
		}
	}
}
