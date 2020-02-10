package intChains.messages;

public class AddRequestMessage {
	private Integer contents;
	
	public AddRequestMessage (Integer contents) {
		this.contents = contents;
	}
	
	public Integer getContents() {
		return contents;
	}
}
