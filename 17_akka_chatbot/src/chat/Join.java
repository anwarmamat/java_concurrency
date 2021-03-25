package chat;

public class Join {
	private final String name;
	private final Integer UID;
	public Join(Integer UID, String name) {
		this.name = name;
		this.UID = UID;
	}
	public String name() { return name;}
	public Integer uid() { return UID;}

}
