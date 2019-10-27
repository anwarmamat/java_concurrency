package akka;

public class Message {
	private final String msg;
	public Message(String message) {
		msg = message;
	}
	
	public String msg() {
		return msg;
	}

}
