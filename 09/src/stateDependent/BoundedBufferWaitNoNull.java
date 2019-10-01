package stateDependent;

/**
 * Class of buffers that attempts to filter out null objects.
 * Suffers from nested-monitor lockout.
 */
public class BoundedBufferWaitNoNull {

	private final BoundedBufferWait buffer;
	
	BoundedBufferWaitNoNull (int capacity) {
		buffer = new BoundedBufferWait(capacity);
	}
	
	public synchronized boolean put (Object elt) throws InterruptedException {
		if (elt != null) {
			buffer.put(elt);
			return true;
		}
		else return false;
	}
	
	public synchronized Object take () throws InterruptedException {
		return buffer.take();
	}
}
