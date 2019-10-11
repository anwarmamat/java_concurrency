package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Flatmap2 {

	public static void main(String[] args) throws IOException {
		
		
		/*
		 * this does not work because the stream is a stream of string[], 
		 * not a stream of words.
		 */
			Files.lines(Paths.get("stuff.txt"))
			//.map(line -> line.split("\\s+")) // Stream<String[]>
			.map(line->line.length())
			.distinct() // Stream<String[]>
			.forEach(System.out::println);
		
			
			String[] arrayOfWords = {"Java", "Magazine"};
			Stream<String> streamOfwords = Arrays.stream(arrayOfWords);
			streamOfwords.forEach(System.out::println);
			
			/**
			 * The solution still doesn’t work. This is because we now 
			 * end up with a list of streams of streams (more precisely 
			 * a Stream<Stream<String>>). Indeed, we first convert each
			 *  line into an array of words, and then convert each array 
			 *  into a separate stream using the method Arrays.stream().
			 */
			
			Files.lines(Paths.get("stuff.txt"))
            .map(line -> line.split("\\s+")) // Stream<String[]>
            .map(Arrays::stream) // Stream<Stream<String>>
            .distinct() // Stream<Stream<String>>
            .forEach(System.out::println);
			
			/**
			 * use flatmap to fix
			 */
			Files.lines(Paths.get("stuff.txt"))
            .map(line -> line.split("\\s+")) // Stream<String[]>
            .flatMap(Arrays::stream) // Stream<String>
            //.distinct() // Stream<String>
            .forEach(System.out::println);
	}

}
