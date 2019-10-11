package streams;

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

public class DistinctExample {

	public static void main(String[] args) {
		Collection<String> list = Arrays.asList("A", "B", "C", "D", "A", "B", "C");
		 
		// Get collection without duplicate i.e. distinct only
		List<String> distinctElements = list.stream().distinct().limit(0).collect(Collectors.toList());
		 
		//Let's verify distinct elements
		System.out.println(distinctElements);

		//Output is:

		//[A, B, C, D]

	}

}
