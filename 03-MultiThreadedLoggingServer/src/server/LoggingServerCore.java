package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utils.DataRecord;

public abstract class LoggingServerCore {
	// Choose a port outside of the range 1-1024:
	public static final int PORT = Integer.getInteger("port", 8080).intValue();
	int port;
	PrintWriter out;
	BufferedReader in;
	ServerSocket serverSocket = null;

	public LoggingServerCore(int port) {
		this.port = port;
	}

	public void go() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// server loop
		while (!Thread.interrupted()) {
			Socket socket = null;
			try {
				
				// block until connection is requested
				socket = serverSocket.accept();
				
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				DataRecord record = new DataRecord(in.readLine().trim());
				// process client message

				process(record);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null) in.close();
					if (socket != null) socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void process(DataRecord record);

}