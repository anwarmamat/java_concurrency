package sum;

import java.util.ArrayList;

public class Worker extends Thread{
	private double answer;
	int lo;
	int hi;
	double[] arr;

	public double getAnswer(){
		return answer;
	}
	
	public Worker(final double[]  input, int lo, int hi){
		arr = input;
		this.lo = lo;
		this.hi = hi;
	}
	
	public void run(){
		//System.out.println(lo +"\t" + hi);
		for(int i=lo; i < hi; i++){
			answer += Process.apply(arr[i]);
		}
	}
	
}
