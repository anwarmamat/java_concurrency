package wordcount;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Manager extends AbstractLoggingActor{
	private int N = 5;	//number of workers
	private ActorRef[] workers;
	private Queue<String> files;
	private Map<String, Integer> map;
	private int doneWorker;
	private String folder_name;
	public Manager() {
		workers = new ActorRef[N];
		files = new LinkedList<>();
		map = new HashMap<>();
	}
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchEquals("start",this::onStart)
				.match(Message.class,this::onMsg)
				.build();
	}
	private void onMsg(Message msg) {
		if(msg.getType() == MessageType.WORD) {
			if(map.containsKey(msg.getMessage())){
				map.put(msg.getMessage(), map.get(msg.getMessage()) + msg.getCount());
			}
			else
				map.put(msg.getMessage(), msg.getCount());
		}
		if(msg.getType() == MessageType.WANT_WORK) {
			if(!files.isEmpty()) {
				String file_name = files.poll();
				Message m = new Message(MessageType.NEW_WORK, file_name);
				getSender().tell(m, getSelf());
			}else {
				Message m = new Message(MessageType.NO_MORE_WORK);//all files processed
				getSender().tell(m, getSelf());
			}
		}
		if(msg.getType() == MessageType.DONE) {
			doneWorker++;
			if(doneWorker >= N) {
				for (Map.Entry<String,Integer> entry : map.entrySet()) {
					System.out.println(entry.getKey() + "\t" + entry.getValue() );

				}
				System.out.println("DONE");
			}
		}
		if(msg.getType() == MessageType.FOLDER) {
			folder_name = msg.getMessage();
		}
	}

	private void onStart(String msg) {
		final File folder = new File(folder_name);
		listFilesFromFolder(folder);//get all the files from the folder
		for(int i=0; i < N; i++) {
			workers[i] = getContext().actorOf(Props.create(Worker.class), "worker-" + i);
			if(!files.isEmpty()) {
				String file_name = files.poll();
				Message m = new Message(MessageType.NEW_WORK, file_name);
				workers[i].tell(m, getSelf());
			}
		}

	}

	private void listFilesFromFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesFromFolder(fileEntry);
			} else {
				files.add(fileEntry.getAbsolutePath());
			}
		}
	}


}
