package readwrite;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLinkedList<T> extends LinkedList<T>{
	
	private List<T> lst;
	private ReadWriteLock lock;
	public ReadWriteLinkedList() {
		lst = new LinkedList<>();
		lock = new ReentrantReadWriteLock();
		
		
	}
	@Override
	public boolean add(T item) {
		Lock writeLock = lock.writeLock();
		writeLock.lock();
			lst.add(item);
		writeLock.unlock();
		return true;
	}
	@Override
	 public T get(int index) {
	        Lock readLock = lock.readLock();
	        readLock.lock();
	        try {
	            return lst.get(index);
	        } finally {
	            readLock.unlock();
	        }
	 }
	@Override
	 public int size() {
		 Lock readLock = lock.readLock();
	     	readLock.lock();
	    try {	
	    	return lst.size();
	    } finally {	
	    	readLock.unlock();
	    }	
	 }	
}
