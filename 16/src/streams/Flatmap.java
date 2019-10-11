package streams;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Flatmap {

	public static void main(String[] args) {
		Stream<String> words = Stream.of("Java", "Magazine", "is", 
			     "the", "best");

			Map<String, Long> letterToCount =
			           words.map(w -> w.split(""))
			                                   .flatMap(Arrays::stream)
			                                   .collect(groupingBy(identity(), counting()));

			System.out.println(letterToCount);
			
			
			
	}

}
