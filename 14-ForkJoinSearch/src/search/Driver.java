package search;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Driver {

	private static void shuffleArray(Integer[] array){
	    int index, temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
	
	
	public static void main(String[] args) {
		int N = 100;
		Integer[] buffer = new Integer[N];
		for(int i = 0; i < N; i++) {
			buffer[i] = i + 1;
		}
		//shuffleArray(buffer);
		int f = 59;
		ForkJoinPool.commonPool().invoke(new Searcher(buffer,0, buffer.length, f));
		
		//ForkJoinPool forkJoinPool = new ForkJoinPool(10);
		//forkJoinPool.invoke(new Searcher(buffer,0, buffer.length, f));
		
	}

}
