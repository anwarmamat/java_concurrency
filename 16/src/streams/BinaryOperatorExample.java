package streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

class Adder implements BinaryOperator<Integer>{

	@Override
	public Integer apply(Integer t, Integer u) {
		return  t + u;
	}

}

public class BinaryOperatorExample {

	public static void main(String[] args) {
		BinaryOperator<Integer> adder = new Adder();
		System.out.println(adder.apply(10,20));
		
		List<Integer> numbers =Arrays.asList(1,2,3,4,5);
		int sum = numbers.stream()
				//.reduce(0, (a,b) -> a + b);	//lambda
				.reduce(0, adder);	//BinaryOperator

		System.out.println(sum);
	}

}
