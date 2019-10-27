package akka1;

import java.util.Random;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Master extends AbstractLoggingActor{
	private final int NUMELTS = 20;   		// Number of elements in array (1,000,000)
	private int NUMCPUS;
	private int NUMACTORS; 
	private double[] input;
	private double total = 0;
	private int taskDone = 0; 
	private long duration;
	public Master() {
		input = createArray(NUMELTS);
		NUMCPUS  = Runtime.getRuntime().availableProcessors();
		NUMACTORS  = 4;//NUMCPUS * 100;
		
	}
	private double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = i+1;//rand.nextInt(100);
            // Don't allow zero values in the input array to prevent divide-by-zero
            if (input[i] == 0.0) {
                i--;
            }
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
		taskDone++;
		if(taskDone == NUMACTORS ){
			duration = System.nanoTime() - duration;
			System.out.println("Duration:" + duration / 1000);
			System.out.println(total);
			
			}
			
	}
	public void onCompute(String s) {
		
		if(s.equals("start")){
			System.out.println("Start");
			duration = System.nanoTime();
		}

		ActorRef[] actors = new ActorRef[NUMACTORS];
		
		Props p = Props.create(Worker.class);
		for(int i = 0; i < NUMACTORS; i++) {
			actors[i] = getContext().system().actorOf(p);
		}
		for(int i = 0; i < NUMACTORS; i++) {
			Task task = new Task (input,   NUMELTS / NUMACTORS * i,  NUMELTS / NUMACTORS * (i + 1));
			actors[1].tell(task, getSelf());
		}

	}
}
