package sum;

import java.util.Random;

public class SequentialSum {
	
	
	private static double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = i+1;//rand.nextInt(100);
        }
        return input;
    }

	public static void main(String[] args) {
		int NUMELTS = 10_000_000; 
		double[] input;
		input = createArray(NUMELTS);
		
		double total = 0;
		
		long duration = System.nanoTime();
		for(int i = 0; i < NUMELTS; i++ ) {
			total += input[i];
		}
		
		duration = System.nanoTime() - duration;
		
		System.out.println("Duration:" + duration / 1000);
		System.out.println(total);
		

	}

}
