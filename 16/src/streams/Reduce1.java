/**
 * 	Java Stream Reduce, lambda,  and BinaryOperator example
 */
package streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class Reduce1 {

	public static void main(String[] args) {
		
		List<Integer> lst = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		int sum = lst.stream().reduce(0, (a, b)->a+b);
		System.out.println(sum);
		
		
		BinaryOperator<Integer> add = (s1, s2) -> s1 + s2;
		
		sum = lst.stream().reduce(0, add);
		System.out.println(sum);
		
		
		
		class Adder implements BinaryOperator<Integer>{
			@Override
			public Integer apply(Integer t, Integer u) {
				return u + t;
			}
		}
		
		sum = lst.stream().reduce(0, new Adder());
		System.out.println(sum);
		
		
	}

}
