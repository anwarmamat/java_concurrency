package sum;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSum extends RecursiveTask<Long> {
	private Integer[] buffer; 
	private int start;
	private int end;
	public ForkJoinSum(Integer[] buffer, int s, int e) {
		this.buffer = buffer;
		this.start = s;
		this.end = e;
	}
	
	@Override
	protected Long compute() {
		StringBuffer bf = new StringBuffer();
		bf.append(Thread.currentThread().getName() + "\tsize:" + (end - start) + "\t");
		
		for(int i = start; i < end; i++) {	
			bf.append(buffer[i]);
				bf.append(",");
		}	
		System.out.println(bf.toString());
		/*
		if(buffer[start] == 6 ) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		/*
		if(buffer[start] == 1  && (end-start <= 1)) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		long sum = 0;
		bf = new StringBuffer();
		if(end - start <= 8) {
			for(int i = start; i < end; i++) {
				sum += buffer[i];
				
				// if one worker sleeps, another works does everything.
				// if(buffer[i] == 5) {  Thread.sleep(2000);}
			
				bf.append(buffer[i]);
				bf.append(",");
			}
			System.out.println(Thread.currentThread().getName() + "\t:" + bf.toString() + "\tsum=" + sum);
			return sum;
		}else {
			int m = (start + end) /  2;
			
			ForkJoinSum left = new ForkJoinSum(buffer, start, m);
			ForkJoinSum right = new ForkJoinSum(buffer, m, end);
			
			
			//left.compute();
			//right.compute();
			

			left.fork();
			
			
			long t = right.compute();
			long s =  t +  left.join();
			
			
			//long s = right.compute() + left.compute();
			
			
			return s;
		}
		
	}	
}
