package streams;

import java.util.ArrayList;

public class GroupBy {

	public static void main(String[] args) {
		
		ArrayList<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(102,1, 1010.0));
		transactions.add(new Transaction(101,2,15.0));
		transactions.add(new Transaction(103,1,1125.0));
		transactions.add(new Transaction(102,1,1200.0));
		
	/*	
		Map<String, Map<Currency, Double>> cityByCurrencyToAverage = 
		           transactions.stream().collect(groupingBy(Transaction::getCity,
		groupingBy(Transaction::getCurrency,  
		averagingInt(Transaction::getValue))));
	*/
	}

}
