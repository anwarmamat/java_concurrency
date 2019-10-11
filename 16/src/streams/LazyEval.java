package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LazyEval {

	public static void main(String[] args) {
		List<Integer> numbers =  Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8); 
		int count = 0;
		List<Integer> sum = numbers.stream() 
	    .filter(n ->  n % 2 == 0) 
	    .map(n ->   n * 2) 
		.collect(Collectors.toList());
		
		System.out.println(sum);
	}

}
