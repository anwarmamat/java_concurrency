package messages;
public class NewNodeMessage {
	private final Integer value;
	public NewNodeMessage(Integer value) {
		this.value = value;
	}
	public Integer getValue() {
		return value;
	}
}
