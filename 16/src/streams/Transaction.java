package streams;

public class Transaction {
	public static final int GROCERY = 1;
	public static final int FUEL = 2;
	private double value = 0;
	private int type;
	private int id;
	public Transaction(int id,int type, double v) {
		this.type = type;
		value = v;
		this.id = id;
	}
	
	public int getType() { return type;}
	public int getId() { return id;}
	public Double getValue() { return value;}
}
