package server;

// Handles writing records to log files in separate threads
// Class in NOT thread safe

import utils.DataRecord;
import utils.MsgHandler;

public class MultiThreadedServer extends LoggingServerCore{

	MultiThreadedServer(int port) {
		super(port);
	}

				
public void process (DataRecord record) {
	(new Thread(new MsgHandler(record))).start();
}


	public static void main(String[] args) {
		System.out.println("Starting server on port:" + PORT);
		MultiThreadedServer s = new MultiThreadedServer(PORT);
		s.go();
		System.out.println("Shutting down server on port:" + PORT);
	}

}