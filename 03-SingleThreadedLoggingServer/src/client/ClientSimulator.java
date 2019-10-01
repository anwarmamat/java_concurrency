package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import utils.DataRecord;

public class ClientSimulator {

	public static final int PORT = Integer.getInteger("port", 8080).intValue();

	int port, runLength, numClients;
	InetAddress addr;

	ClientSimulator(InetAddress addr, int port, int numClients, int runLength) {
		this.port = port;
		this.addr = addr;
		this.numClients = numClients;
		this.runLength = runLength;
	}

	public void go() {
		Socket socket;
		PrintWriter outRemote;

		for (int i = 0; i < runLength; i++) {
			for (int j = 0; j < numClients; j++) {
				try {
					socket = new Socket(addr, port);
					outRemote = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())),
							true);
					outRemote.println(String.valueOf(i) + DataRecord.SEP + String.valueOf(j));

					outRemote.close();
					socket.close();

				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}
	}

	public static void main(String[] args) {
		InetAddress addrTest;
		long startTime = System.currentTimeMillis();
		try {
			addrTest = InetAddress.getByName(System.getProperty("host"));
			ClientSimulator testClient = new ClientSimulator(addrTest, PORT,
					25, 25);
			testClient.go();
		} catch (UnknownHostException e) {
			System.err.println(e);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Elapsed Time:" + (endTime - startTime));
	}

}