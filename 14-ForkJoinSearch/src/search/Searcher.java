package search;

import java.util.concurrent.RecursiveAction;

public class Searcher extends RecursiveAction{

	private Integer[] buffer; 
	private int start;
	private int end;
	private int f; 	//number to find
	public Searcher(Integer[] buffer, int s, int e, int f) {
		this.buffer = buffer;
		this.start = s;
		this.end = e;
		this.f = f;
	}
	
	@Override
	protected void compute() {
		
		if(end - start <= 25) {
			synchronized(this) {
				StringBuffer bf = new StringBuffer();
				bf.append(Thread.currentThread().getName());
				bf.append("\n");
				for(int i = start; i < end; i++) {
					bf.append(buffer[i]+",");
					if(buffer[i] == f) bf.append("---found---");
					
				}
				System.out.println(bf.toString());
			}
		}else {
			int mid = (start + end) /2;
		    Searcher left = new Searcher(buffer, start, mid,f);
		    Searcher right = new Searcher(buffer, mid, end,f);
		    
		    left.fork();
		    //right.fork();
            right.compute();
            
            left.join();
            
            
            //right.join();
            
            
         
		}
	}
	

}
