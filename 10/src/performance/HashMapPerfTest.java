package performance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * From http://teremail.googlecode.com/hg-history/c1e70415c3e77dd47df19a75b04052ecb31b33db/commons/src/test/java/org/teresoft/collections/HashMapPerfTest.java
 *
 */
public class HashMapPerfTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
        Random r = new Random(12312);
        byte[] someData = new byte[5];
        String[] dataTable = new String[20000];
        for (int i = 0, n = dataTable.length; i < n; i++) {
            r.nextBytes(someData);
            dataTable[i] = new String(someData);
        }
        
        Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
        int iterations = 10000000;
        String value = null;
        
        long t0 = System.currentTimeMillis();
        
        for (int i = 0; i < iterations; i += 2) {
            int index = i % dataTable.length;
            map.put(dataTable[index], dataTable[index + 1]);
            value = map.get(dataTable[index]);
        }
        
        long t1 = System.currentTimeMillis();
        
        if (value == null) {
            throw new RuntimeException();
        }
        
        long ops = (iterations * 1000) / (t1-t0);
        System.out.printf("Operations per second for synchronizedMap: %,d%n", ops);

	}

}
