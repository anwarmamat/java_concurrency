package weak;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WeaklyConsistentIterator {

	public static void main(String[] args) {
		List<String> input = Arrays.asList("a", "b", "c", "d", "e");
		List<String> output = new ArrayList<>();

		Deque<String> deque = new ConcurrentLinkedDeque<>(input);
		
		
		
		
		
		
		for (String s : deque) {
			output.add(s);
			/**
				a      b	c    d     e
				
				            |
				         iterator
		add XXX                      delete e  
				 
			 */
		
			
			if (s.equals("c")) {
				deque.addFirst("XXX");  //will not be added to output, because it was added to the left of the iterator
				deque.removeLast();	    // "e" will not be added to the output 
										// because it was removed from the right of the iterator.
										
			}
		}
		
		
		System.out.println("Input: " + input);
		System.out.println("Queue: " + deque);
		/**
		 * XXX" was added before the "e" was removed, so the output 
		 * list reflects only some of the modifications that were made 
		 * to the input during the iteration. Since iteration proceeds 
		 * left-to-right in this case, it's not surprising that modifications 
		 * made to the left of the current iteration point aren't seen, 
		 * and modifications made to the right of the current iteration 
		 * point are seen. Of course, not all collections have such a 
		 * straightforward iteration order.
		 */
		System.out.println("Output: " + output);
	}
}
