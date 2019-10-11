package streams;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelStreamDemo {
	private static final int N = 10000000; 
	ArrayList<Double> numbers;
	public ParallelStreamDemo() {
		numbers = new ArrayList<>();
	}
	
	private void GenerateNumbers() {
		numbers.clear();
		for(int i= 1; i <= N; i++) {
			numbers.add(Math.random());
		}
	}
	
	public double seqSum() {
		double sum = numbers.stream().
				reduce(0.0, (a,b)->a+b);
		return sum;
	}
	
	public double parSum() {
		double sum = numbers.parallelStream().
				reduce(0.0, (a,b)->a+b);
		return sum;
	}
	
	
	public static void main(String[] args) {
			
		ParallelStreamDemo seq = new ParallelStreamDemo();
			
			double sumTime1 = 0;
			double sum1 = 0;
			
			double sumTime2 = 0;
			double sum2 = 0;
			long startTime1 = 0;
			double s1 = 0;;
			long endTime1 = 0;
			
			long startTime2 = 0;
			double s2 = 0;
			long endTime2 = 0;
			
			for(int i = 0; i < 10; i++) {
				seq.GenerateNumbers();
				if( i % 2 == 0) {
					startTime1 = System.currentTimeMillis();
					s1 = seq.seqSum();
					//System.out.println("seq");
					endTime1 = System.currentTimeMillis();
				
					startTime2 = System.currentTimeMillis();
					s2 = seq.parSum();
					//System.out.println("par");
					endTime2 = System.currentTimeMillis();
				}else {
				
					startTime2 = System.currentTimeMillis();
					s2 = seq.parSum();
					//System.out.println("par");
					endTime2 = System.currentTimeMillis();
				
					startTime1 = System.currentTimeMillis();
					s1 = seq.seqSum();
					//System.out.println("seq");
					endTime1 = System.currentTimeMillis();
			}
				sumTime1 += (endTime1 - startTime1);
				sum1 += s1;
				sumTime2 += (endTime2 - startTime2);
				sum2 += s2;
			}
			System.out.println("seq sum  = "  + sum1);
			System.out.println("Sequential Sum Time:" + sumTime1 / 10);
			
			System.out.println("par sum  = "  + sum2);
			System.out.println("Paralell Sum Time:" + sumTime2 / 10);
			
			//long startTime2 = System.currentTimeMillis();
			//double s2 = seq.parSum();
			//long endTime2 = System.currentTimeMillis();
			
			//System.out.println("Elapsed Time:" + (endTime - startTime));
			//System.out.println("Elapsed Time2:" + (endTime2 - startTime2));
			
			//System.out.println(s1);
			//System.out.println(s2);

	}

}
