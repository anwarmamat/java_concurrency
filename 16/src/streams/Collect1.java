package streams;
import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; 
public class Collect1 {

	public static void main(String[] args) {
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(102,1, 1010.0));
		transactions.add(new Transaction(101,2,15.0));
		transactions.add(new Transaction(103,1,1125.0));
		transactions.add(new Transaction(102,1,1200.0));
		
		List<Integer> expensiveTransactionsIds = 
		       transactions.stream()
		       	.filter(t -> t.getValue() > 1000)
		       	.map(Transaction::getId)
		       	.collect(toList());

	
		System.out.println(expensiveTransactionsIds);
		
		/**
		 * Collect to a set
		 */
		Set<Integer> ids = 
		         transactions.stream()
		         .filter(t -> t.getValue() > 1000)
		         .map(Transaction::getId)
		         .collect(toSet());
	
		System.out.println(ids);
		
		/**
		 * Ask for a hashset
		 */
		Set<Integer> ids2 = 
		         transactions.stream()
		         	.filter(t -> t.getValue() > 1000)
		         	.map(Transaction::getId)
		         	.collect(toCollection(HashSet::new));
		
		System.out.println(ids2);
	
	
	
		long howManyTransactions = 
	          transactions.stream().collect(counting());
		
		System.out.println(howManyTransactions);
		
		Double totalValue = transactions.stream().collect(Collectors.summingDouble(Transaction::getValue));
		
		System.out.println(totalValue);
		
		double average = transactions.stream().collect(
				Collectors.averagingDouble(Transaction::getValue));
		
		System.out.println(average);
	}
}
