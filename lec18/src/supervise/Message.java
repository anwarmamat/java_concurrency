package supervise;

public class Message {
	private final String msg;
	public Message(String msg) {
		this.msg = msg;
	}
	
	public String get() {
		return msg;
	}
}
