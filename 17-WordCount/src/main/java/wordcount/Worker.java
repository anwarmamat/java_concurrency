package wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractLoggingActor;

public class Worker extends AbstractLoggingActor {
	private Map<String, Integer> map;

	public Worker() {
		map = new HashMap<>();
	}
	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Message.class, this::onMessage).build();
	}

	private void onMessage(Message msg) {
		if (msg.getType() == MessageType.NEW_WORK) {
			wordCount(msg.getMessage());
			Message m = new Message(MessageType.WANT_WORK);
			getSender().tell(m, getSelf());
		}
		if (msg.getType() == MessageType.NO_MORE_WORK) {
			for (Map.Entry<String,Integer> entry : map.entrySet()) {
				Message m = new Message(MessageType.WORD, entry.getKey(),entry.getValue() );
				getSender().tell(m, getSelf());
			}
			Message m = new Message(MessageType.DONE);
			getSender().tell(m, getSelf());
		}
	}

	private void wordCount(String filename) {
		File f_in = new File(filename);
		String[] words = null;
		FileReader fr;
		try {
			fr = new FileReader(f_in);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null) {
				words = s.split("\\W+");
				for (String w : words) {
					if (map.containsKey(w))
						map.put(w, map.get(w) + 1);
					else
						map.put(w, 1);
				}
			}
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
