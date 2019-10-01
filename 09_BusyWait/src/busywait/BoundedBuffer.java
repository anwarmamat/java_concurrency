package busywait;

import java.util.ArrayList;

public class BoundedBuffer {
	
	private final int maxSize;
	private ArrayList<Object> elements;
	public static volatile boolean full = false;
	
	BoundedBuffer (int maxSize) {
		this.maxSize = maxSize;
		elements = new ArrayList<Object> ();
	}
	
	public synchronized void put (Object elt) {
		//while(full){
			//System.out.println("full");
//			Thread.yield();
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		//}
		elements.add(elt);
		if(elements.size() == maxSize) {
			full = true;
		}
		System.out.println("put: " + elt);
	}
	
	
	public  synchronized Object take() {
		while(!full) {
//			//System.out.println("empty");
//			Thread.yield();
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		Object elt = elements.get(0);
		elements.remove(0);
		if(elements.isEmpty()) {
			full = false;
		}
		System.out.println("take: " + elt);
		return elt;
	}
}
