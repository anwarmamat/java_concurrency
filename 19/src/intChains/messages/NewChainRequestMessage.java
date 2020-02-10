package intChains.messages;

public class NewChainRequestMessage {

	private final Integer contents;
	
	public NewChainRequestMessage(Integer contents) {
		this.contents = contents;
	}
	
	public Integer getContents() {
		return contents;
	}
}
