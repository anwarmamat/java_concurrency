package streams;

import java.util.stream.Stream;

public class LimitExample {

	public static void main(String[] args) {
		
		System.out.println("Limit");
		Stream.of(1,2,3,4,5,6,7,8,9)
        .peek(x->System.out.print("\nA"+x))
        .limit(3)
        .peek(x->System.out.print("B"+x))
        .forEach(x->System.out.print("C"+x));
		
		
		System.out.println("\n\nSkip");
		
		Stream.of(1,2,3,4,5,6,7,8,9)
	    .peek(x->System.out.print("A"+x))
	    .skip(6)
	    .peek(x->System.out.print("B"+x))
	    .forEach(x->System.out.println("C"+x));
		
		System.out.println("\n\nList + Skip");
		
		Stream.of(1,2,3,4,5,6,7,8,9)
	    .peek(x->System.out.print("A"+x))
	    .limit(4)
	    .skip(2)
	    .forEach(x->System.out.print("B"+x));
		
		
		System.out.println("\n\nList + Skip");
		
		Stream.of(1,2,3,4,5,6,7,8,9)
	    .peek(x->System.out.print("A"+x))
	    .limit(2)
	    .skip(4)
	    .forEach(x->System.out.print("B"+x));
		

	}

}
