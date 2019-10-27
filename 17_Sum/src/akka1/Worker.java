package akka1;

import akka.actor.AbstractActor;

public class Worker extends AbstractActor{
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Task.class, this::onCompute).build();
		
	}
	
	public void onCompute(Task m) {
		Double sum = 0.0;
		for(int i = m.start; i < m.end; i++) {
			sum += m.input[i];
		}
		System.out.println(sum);
		getSender().tell(sum, getSelf());
	}

}
