package sum;

import java.util.ArrayList;

public class Worker extends Thread{ 
	double[] input;
	private double ans;
	private int lo;
	private int hi;
	public double getAnswer() {return ans;}
	public Worker(double[] input, int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		this.input = input;
	}
	
	
	@Override
	public void run() {
		for(int i = lo; i < hi; i++) {
			double a = input[i] + 0.5;
			double b = 1.0 / a;
			double c = Math.sqrt(b);
			ans += c;
		}
	}
}
