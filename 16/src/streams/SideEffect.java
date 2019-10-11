package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
class Transfer{
	private long value;
	public Transfer(long v) { value = v;}
	public long getValue() { return value;}
}

class Account {
	   private long total = 0;
	   public void process(Transfer transaction) {
	       add(transaction.getValue());
	   }
	   public void add(long amount) {
	       total += amount;
	   }

	   public long getAvailableAmount(){
	       return total;
	   }
	}

*/

class Account {
	   private long total = 0;
	   public void process(Integer item) {
	       add(item);
	   }
	   /**
	    * Must be synchronized for parallel stream
	    * @param amount
	    */
	   public  void add(long amount) {
		   //synchronized(this) {
	       total += amount;
		   //}
	   }

	   public long getAvailableAmount(){
	       return total;
	   }
	   public void clear() { total = 0;}
	}

public class SideEffect {

	public static void main(String[] args) {
		Account account = new Account();
		
		List<Integer> items = new ArrayList<>();
		items.add(10);
		items.add(30);
		items.add(20);
		
		for(Integer item: items) {
		   account.process(item);
		}
		System.out.println(account.getAvailableAmount());

		
		items.stream()
        .forEach(account::process);
		System.out.println( account.getAvailableAmount());
		
		
		
		
		List<Integer> items2
		   = IntStream.rangeClosed(0, 1_000)
		               .mapToObj(Integer::new)
		               .collect(Collectors.toList());

		
		
		account.clear();
		/**
		 * This code has side effect. 
		 * 
		 * Multiple threads are trying to read, modify, 
		 * and update the shared state of the account
		 */
		items2.parallelStream()
		.forEach(account::process);
		
			System.out.println( "parallelstream sum:" + account.getAvailableAmount());
			
			
		account.clear();	
		
		long sum = items2.parallelStream()
                    .mapToLong(s->(long)s)
                    .sum();
			account.add(sum);
		System.out.println(account.getAvailableAmount());

	}

}
