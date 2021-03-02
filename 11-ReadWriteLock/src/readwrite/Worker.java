package readwrite;

import java.util.List;
import java.util.Random;

public class Worker extends Thread {
	private List<Integer> lst;
	private int n = 1000;
	private Random r = new Random();
	public Worker(List<Integer> lst, int n) {
		this.lst = lst;
		this.n = n;
	}
	public void run () {
		for(int i = 0; i < n; i++) {
			Integer item = r.nextInt();	
			lst.add(item);
			int s = lst.size();
			for(int j = 0; j < 100; j++) {
				int idx = r.nextInt(s);	
				lst.get(idx);
			}
		}
	}
}
