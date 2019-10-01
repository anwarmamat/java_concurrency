package threadLocal2;
import java.util.ArrayList;

/**
 * Example showing how ThreadLocal, local variables used to ensure thread safety,
 * even when non-thread-safe objects are used.  Manager threads spawn worker threads,
 * recording the worker-thread ids in the ThreadLocal workerIds.  The output string
 * is computed using a local variable.

 *
 */
public class ManagerThread implements Runnable {
	
	private ThreadLocal<ArrayList<Long>> workerIds
	= new ThreadLocal<ArrayList<Long>> (){
		protected ArrayList<Long> initialValue () {
			return new ArrayList<Long> ();
		} // Anonymous overriding needed to create proper initialValue () method
		  // for ThreadLocal variable.
	};
	
	//private ArrayList<Long> workerIds = new ArrayList<>();
	
	private int numWorkers;
	
	ManagerThread (String name, int n) {
		
		//this.setName (name);
		numWorkers = n;
	}

	/**
	 * Create, start new worker thread.
	 * 
	 * Precondition:  none
	 * Postcondition:  new worker thread created, id stored in threadIds
	 * Exception:  none
	 */
	private void startWorker () {
		WorkerThread t = new WorkerThread ();
		
		ArrayList<Long> myWorkerIds = workerIds.get();
		//ArrayList<Long> myWorkerIds = workerIds;
		myWorkerIds.add(t.getId());
		t.start ();
	}

	/* Start all the workers.
	 * 
	 * Precondition:  none
	 * Postcondition:  numWorker number of threads started, ids printed
	 * Exception:  none
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run () {
		for (int i = 0; i < numWorkers; i++) startWorker();
		System.out.println (Thread.currentThread().getName() + " worker ids: " + workerIds.get());
		//System.out.println (Thread.currentThread().getName() + " worker ids: " + workerIds);
	}

}
