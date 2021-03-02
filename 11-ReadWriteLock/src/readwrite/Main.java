package readwrite;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int n = 1000;
		int numThreads = 20;
		Random r = new Random();
		List<Integer> lst1 = new ReadWriteLinkedList();//perform better
		List<Integer> lst2 = Collections.synchronizedList(new LinkedList());
		
		
		Thread[] workers = new Thread[numThreads];
		for(int i = 0; i < numThreads; i++) {
			workers[i] = new Worker(lst1,n);
		}
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < numThreads; i++) {
				workers[i].start();
		}
			
		try {
			for(int i = 0; i < numThreads; i++) {
				workers[i].join();	
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		for(int i = 0; i < numThreads; i++) {
			workers[i] = new Worker(lst2,n);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("ReadWriteLock: Elapsed Time:" + (endTime - startTime));
		System.out.println("Size:" + lst1.size());
		
		startTime = System.currentTimeMillis();
		for(int i = 0; i < numThreads; i++) {
			workers[i].start();
		}
		
		try {
		for(int i = 0; i < numThreads; i++) {
			workers[i].join();	
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Mutex Lock: Elapsed Time:" + (endTime - startTime));
		System.out.println("Size:" + lst2.size());
	}

}
