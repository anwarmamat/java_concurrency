package prime;

import java.util.ArrayList;
import java.util.List;

public class Message {
	public final int lo,hi;
	public final List<Integer> list; 
	public Message(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		list = new ArrayList<>();
	}
	public List<Integer> get(){ return list;}
	
}
