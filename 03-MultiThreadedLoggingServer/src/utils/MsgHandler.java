package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MsgHandler implements Runnable {

	protected final DataRecord record;
	protected PrintWriter out;

	public MsgHandler(DataRecord record) {
		this.record = record;
	}

	public void run() {
		System.out.println("Server Side: writing record:" + record);
		double t = 0;
		for(int i = 0; i < 1000; i++){
			for(int j = 0; j < 1000; j++){
				t += (50.0 / (i + 0.7)) + (50 / (j + 0.7));
			}
		}
		writeMsg(t);
	}

	protected void writeMsg(double t) {
		try {
			out = new PrintWriter(new FileWriter(record.getSender()
					+ ".txt", true));
			out.print(t);
			out.flush();
			out.print("Begin Record:");
			out.flush();
			out.print("Sender:" + record.getSender() + ":");
			out.flush();
			out.print("Message:" + record.getMsg() + ":");
			out.flush();
			out.println("End Record");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
