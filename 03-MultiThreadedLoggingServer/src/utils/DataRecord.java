package utils;

public class DataRecord {

	final String sender, msg;

	public static final String SEP = "###";

	public DataRecord(String stringRep) {
		String[] parts = stringRep.split(SEP);
		if (parts.length == 2) {
			this.sender = parts[0];
			this.msg = parts[1];
		} else {
			this.sender = this.msg = "";
		}
	}

	public String getSender() {
		return sender;
	}

	public String getMsg() {
		return msg;
	}

	public String toString() {
		return sender + SEP + msg;
	}
}
