package filemanager;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore; 

public class FileManager {
	
	private Semaphore writer = new Semaphore(1);
	private volatile int reader = 0;
	public void write() {
		//while(reader > 0) {
		if(reader >= 0) {
			try {
				writer.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Writer starts.");
		final int DELAY = 100;
	    try{
	      Thread.sleep( DELAY);
	    }
	    catch (InterruptedException e) {}
	    System.out.println("Writer stops.");
	    writer.release();
	}
	
	public void read() {
		synchronized(this) {	
				try {
					if(reader == 0){
						System.out.println("Filed Locked.");
						writer.acquire();
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 reader++;
			 System.out.println("Reader " + reader + " starts reading.");
		}//release 
		
		final int DELAY = 500;
	    try
	    {
	      Thread.sleep( DELAY);
	    }
	    catch (InterruptedException e) {}
	    
		synchronized(this)
	    {
	      System.out.println("Reader " + reader + " stops.");
	      this.reader--;
	      if (this.reader == 0)
	      {
	        writer.release();
	        System.out.println("File Unlocked.");
	      }
	    }
		
	}
}
