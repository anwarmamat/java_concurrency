package wait;

public class Buffer {
	private int count = 0;
	public volatile boolean full = false;
	public volatile boolean empty = true;
	
}
