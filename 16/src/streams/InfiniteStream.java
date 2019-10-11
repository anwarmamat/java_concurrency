package streams;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfiniteStream {

	public static void main(String[] args) {
		Stream<Integer> numbers = Stream.iterate(0,n->n+10);

		//int sum = numbers.limit(5).reduce(0, (a,b)->a+b);
		//System.out.println(sum);
		
		//this runs forever.
		int sum = numbers.limit(10).reduce(0, (a,b)->a+b);
		
		//numbers.limit(5).forEach(System.out::println); 
		// 0, 10, 20, 30, 40

		System.out.println(sum);
		/*
		Stream<Integer> numbers2 = Stream.iterate(1,n->n+1); //Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20);
		List<Integer> list = numbers2.limit(50)
		  .filter( n -> { return (Math.sqrt(n) - Math.floor(Math.sqrt(n))) < 0.005; })
		 //.map( n -> { return (int)(Math.sqrt(n) + 1); } )
		 .skip(3)
		 .collect(Collectors.toList());
		
		System.out.println(list);*/
	}

}
