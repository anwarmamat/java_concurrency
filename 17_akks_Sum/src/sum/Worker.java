package sum;

import akka.actor.AbstractActor;

public class Worker extends AbstractActor{
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Task.class, this::onCompute).build();
	}
	// Worker computes the partial sum
	public void onCompute(Task m) {
		Double sum = 0.0;
		for(int i = m.start; i < m.end; i++) {
			sum += m.input[i];
		}
		System.out.println(getSelf().path().name() + ":\t" + sum);
		getSender().tell(sum, getSelf());
	}

}
