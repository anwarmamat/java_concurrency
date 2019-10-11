/**
 * The following are examples of using the predefined collectors to 
 * perform common mutable reduction tasks:

 */
package streams;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class CollectorsExample {

	public static void main(String[] args) {
		
		/**
		 * Create a people stream here.
		 */
		ArrayList<Person> people = new ArrayList<>();
		people.add(new Person("alice", 100,"CS"));
		people.add(new Person("bob", 200,"Math"));
		people.add(new Person("cathy", 300, "Math"));
		people.add(new Person("david", 400, "CS"));
		people.add(new Person("bob", 400, "ENG"));
		
		
		     // Accumulate names into a List
		     List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());

		     System.out.println(list);
		     // Accumulate names into a TreeSet
		     Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
		     System.out.println(set);
		     
		     // Convert elements to strings and concatenate them, separated by commas
		     String joined = people.stream()
		                           .map(Object::toString)
		                           .collect(Collectors.joining(", "));

		     System.out.println(joined);
		     
		     // Compute sum of salaries of employee
		     int total = people.stream()
		                          .collect(Collectors.summingInt(Person::getSalary));

		     System.out.println(total);
		                          
		     // Group employees by department
		     Map<String, List<Person>> byDept
		         = people.stream()
		                    .collect(Collectors.groupingBy(Person::getDepartment));
		     
		     System.out.println(byDept);
		     
		     
		     // Compute sum of salaries by department
		     Map<String, Integer> totalByDept
		         = people.stream()
		                    .collect(Collectors.groupingBy(Person::getDepartment,
		                                                   Collectors.summingInt(Person::getSalary)));
		     
		     System.out.println(totalByDept);
		     
		     
		     // Partition students into passing and failing
		     Map<Boolean, List<Person>> passingFailing =
		         people.stream()
		                 .collect(Collectors.partitioningBy(s -> s.getSalary() >= 300));

		     System.out.println(passingFailing);
	}

}
