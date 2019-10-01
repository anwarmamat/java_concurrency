package server;

import utils.DataRecord;
import utils.MsgHandler;

public class SingleThreadedServer extends LoggingServerCore {

	SingleThreadedServer(int port) {
		super(port);
	}


	public void process(DataRecord record) {
		(new MsgHandler (record)).run();
		//wait until finish, then take next request
	}
	
	
	public static void main(String[] args) {
		System.out.println("Starting server on port:" + PORT);
		
		SingleThreadedServer s = new SingleThreadedServer(PORT);
		s.go();
		
		System.out.println("Shutting down server on port:" + PORT);
	}
}