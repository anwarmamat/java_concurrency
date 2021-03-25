package chat;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;

public class Server extends AbstractLoggingActor{
	private Map<Integer,String> students = new HashMap<>();
	private Map<Integer,String> messages = new HashMap<>();
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
	    		.match(Message.class, this::onMessage)
	    		.match(Join.class, this::onJoin)	
	    		.match(GoodBye.class, this::onGoodBye)	
	        .build();
	}
	public void onJoin(Join message) {
		Integer uid = message.uid();
		String name = message.name();
		log().info("chatbot received: Join: uid:" + uid + "\tname:" + name  );
		ActorRef sender = getSender();
		if(!students.containsKey(uid)) {
			students.put(uid, name);
			sender.tell("Received", null);
		}	
	}
	public void onMessage(Message message) {
		Integer uid = message.uid();
		String msg = message.msg();
		log().info("chatbot received: Join: uid:" + uid + "\tmessage:" + msg  );
		ActorRef sender = getSender();
		if(students.containsKey(uid)) {
			if(!messages.containsKey(uid)) {
				messages.put(uid, msg);
			}else {
				messages.put(uid, messages.get(uid) + ";\t" + msg);
			}
		}
		sender.tell("Received", null);
	}
	
	public void onGoodBye(GoodBye msg) {
		log().info("chatbot received: Goodbye" );
		 for (Map.Entry<Integer,String> entry : messages.entrySet()){
			 	String name = students.get(entry.getKey());
	            System.out.println("Name:" + name + "\tUID = " + entry.getKey() + 
	                             ", Message = " + entry.getValue()); 
	    } 	
	}
}
