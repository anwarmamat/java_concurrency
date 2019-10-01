package performance;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Code for testing performance of ConcurrentHashMaps vs. synchronized HashMaps.  This is
 * adapted from HashMapPerfTest.
 *
 */
public class ConcSyncHashMapPerfTest {

	private static class MapAccessorThread extends Thread {
		private String[] dataTable;
		private int numIterations;
		private Map<String,String> map;
		MapAccessorThread (String[] dataTable, Map<String,String> map, int numIterations) {
			this.dataTable = dataTable;
			this.map = map;
			this.numIterations = numIterations;
		}
		
		public void run() {
			String value = null;
	        for (int i = 0; i < numIterations; i += 2) {
	            int index = i % dataTable.length;
	            map.put(dataTable[index], dataTable[index + 1]);
	            value = map.get(dataTable[index]);
	        }
		}
	}

	public static void main(String[] args) {

        Random r = new Random(12312);
        byte[] someData = new byte[5];
        String[] dataTable = new String[20000];
        for (int i = 0, n = dataTable.length; i < n; i++) {
            r.nextBytes(someData);
            dataTable[i] = new String(someData);
        }
        
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String> ();
        int iterations = 10000000;
        int numThreads = 1000;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
        	threads[i] = new MapAccessorThread (dataTable, map, iterations / numThreads);
        }
        
        long t0 = System.currentTimeMillis();
        
        for (int i = 0; i < numThreads; i++) {
        	threads[i].start();
        }
        
        for (int i=0; i < numThreads; i++) {
        	try { threads[i].join(); } catch (InterruptedException e) { }
        }
        
        long t1 = System.currentTimeMillis();
        
        long ops = (iterations * 1000) / (t1-t0);
        System.out.printf("Operations per second for ConcurrentHashMap: %,d%n", ops);

	}

}
