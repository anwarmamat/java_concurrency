package sum;

import java.util.ArrayList;
import java.util.Random;


public class SumMultiThread {
	// Number of times to repeat each test, for consistent timing results.
    final static private int REPEATS = 10;

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    private double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = i+1;//rand.nextInt(100);
            // Don't allow zero values in the input array to prevent divide-by-zero
            if (input[i] == 0.0) {
                i--;
            }
        }
        return input;
    }

    /**
     * A reference implementation of seqArraysum, in case the one in the main source file is accidentally modified.
     *
     * @param input Input to sequentially compute a reciprocal sum over
     * @return Reciprocal sum of input
     */
    private double seqArraySum(final double[] input) {
        double sum = 0;

        // Compute sum of reciprocals of array elements
        for (int i = 0; i < input.length; i++) {
        		double a =  (input[i] + 0.5);
        		double b = 1.0 / a;
        		double c = Math.sqrt(b);
            sum += c;
        }
        return sum;
    }
    
    
    private double parManyTaskArraySum(final double[]  input, int ntasks) throws InterruptedException{
    	Worker[] ts = new Worker[ntasks];
    	int len = input.length; 
    	  
    	for(int i=0; i < ntasks; i++){
    	    ts[i] = new Worker(input, len / ntasks * i,  len / ntasks * (i + 1));
    	    ts[i].start();
    	}
    	
    	double ans = 0; // combine results
    	for(int i=0; i < ntasks; i++){
    		ts[i].join();
    		ans += ts[i].getAnswer();
    	}
    	  return ans;
    }

    /**
     * A helper function for tests of the two-task parallel implementation.
     *
     * @param N The size of the array to test
     * @param useManyTaskVersion Switch between two-task and many-task versions of the code
     * @param ntasks Number of tasks to use
     * @return The speedup achieved, not all tests use this information
     * @throws InterruptedException 
     */
    private double parTestHelper(final int N,  final int ntasks) throws InterruptedException {
        // Create a random input
        final double[] input = createArray(N);
        // Use a reference sequential version to compute the correct result
        final double correct = seqArraySum(input);
        // Use the parallel implementation to compute the result
        double sum = 0.0;
        
        sum = parManyTaskArraySum(input, ntasks);
        
        final double err = Math.abs(sum - correct);
        // Assert the expected output was produced
        final String errMsg = String.format("Mismatch in result for N = %d, expected = %f, computed = %f, absolute " +
                "error = %f", N, correct, sum, err);
        
        assert(err < 1E-2);

        /*
         * Run several repeats of the sequential and parallel versions to get an accurate measurement of parallel
         * performance.
         */
        final long seqStartTime = System.currentTimeMillis();
        for (int r = 0; r < REPEATS; r++) {
            seqArraySum(input);
        }
        final long seqEndTime = System.currentTimeMillis();

        final long parStartTime = System.currentTimeMillis();
        for (int r = 0; r < REPEATS; r++) {
            parManyTaskArraySum(input, ntasks);
        }
        final long parEndTime = System.currentTimeMillis();

        final long seqTime = (seqEndTime - seqStartTime) / REPEATS;
        final long parTime = (parEndTime - parStartTime) / REPEATS;

        System.out.println("Sequentil sum:" + correct);
        System.out.println("parallel  sum:"+ sum);
        System.out.println("Sequentil sum time:" + seqTime);
        System.out.println("parallel sum time:"+ parTime);
        
        double speedup = (double)seqTime / (double)parTime;
        		
        System.out.println("speedup:" + speedup);
        return speedup;
    }
	
	public static void main(String[] args) throws InterruptedException {
		SumMultiThread s = new SumMultiThread();
		final double speedup = s.parTestHelper(20_000_000, 8);
	}
}
