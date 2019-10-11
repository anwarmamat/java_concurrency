package streams;

import java.util.Arrays;
import java.util.List;

public class Associative {
	static List<String> letters = Arrays.asList("a", "b","c","d","e", "f", "g", "h");
	    public static void main(String[] args) {
	        /** Sequence stream and Reduction function: (x, y) -> x + y
	         *  Output: abcdefgh
	         */
	        System.out.println(letters.stream()
	                        .parallel()
	                        .reduce("", (x, y) -> x + y)
	                        );
	        /** Sequence stream and Reduction function:(x, y) -> y + x + y
	         *  Output: hgfedcbaabcdefgh
	         */
	        System.out.println(letters.stream()
	                .reduce("", (x, y) -> y + x + y)
	        );
	        /** Parallel stream and Reduction function: (x, y) -> y + x + y)
	         *  Output: hhgghhffeeffhhgghhddccddbbaabbddccddhhgghhffeeffhhgghh
	         */
	        System.out.println(letters.stream()
	                .parallel()
	                .reduce("", (x, y) -> y + x + y)
	        );
	    }
}
