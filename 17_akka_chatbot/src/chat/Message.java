package chat;

public class Message {
	private final String msg;
	private final Integer UID;
	public Message(Integer UID, String msg) {
		this.UID = UID;
		this.msg = msg;
	}
	
	public String msg() {
		return msg;
	}
	public Integer uid() {
		return UID;
	}
}
