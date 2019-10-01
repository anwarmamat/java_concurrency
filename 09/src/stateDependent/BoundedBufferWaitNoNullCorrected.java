package stateDependent;

/**
 * Corrected class of bounded buffers that filter out null objects.
 */
public class BoundedBufferWaitNoNullCorrected {
	
	private final BoundedBufferWaitLockParam buffer;
	
	BoundedBufferWaitNoNullCorrected (int capacity) {
		buffer = new BoundedBufferWaitLockParam (this, capacity);
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
