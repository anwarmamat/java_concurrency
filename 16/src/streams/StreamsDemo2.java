package streams;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamsDemo2 {

	public static void main(String[] args) {
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(100,1, 10.0));
		transactions.add(new Transaction(101,2,15.0));
		transactions.add(new Transaction(103,1,25.0));
		transactions.add(new Transaction(102,1,20.0));
		
		
		List<Transaction> groceryTransactions = new ArrayList<>();
		for(Transaction t: transactions){
		  if(t.getType() == Transaction.GROCERY){
		    groceryTransactions.add(t);
		  }
		}
		Collections.sort(groceryTransactions,(a,b)->(a.getValue().compareTo(b.getValue())));
		
		List<Integer> transactionIds = new ArrayList<>();
		for(Transaction t: groceryTransactions){
		  transactionIds.add(t.getId());
		}
		
		System.out.println(transactionIds);
			
		List<Integer> transactionsIds = 
			    transactions.parallelStream()
			                .filter(t -> t.getType() == Transaction.GROCERY)
			                .sorted(Comparator.comparing(Transaction::getValue).reversed())
			                .map(Transaction::getId)
			                .collect(Collectors.toList());
		
		
		System.out.println(transactionsIds);
		
	}

}
