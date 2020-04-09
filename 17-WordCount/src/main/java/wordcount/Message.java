package wordcount;

public class Message {
	final private MessageType msgType;
	final private String msg;
	final private int count;
	public Message(MessageType type){
		msgType = type;
		this.msg = "";
		count = 0;
	}
	public Message(MessageType type, String msg){
		msgType = type;
		this.msg = msg;
		count = 0;
	}
	public Message(MessageType type, String msg,int cnt){
		msgType = type;
		this.msg = msg;
		count = cnt;
	}
	
	public String getMessage() { return msg;}
	public MessageType getType() { return msgType;}
	public int getCount() { return count;}
}
