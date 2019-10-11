package streams;

public class Person {
	private String name;
	private int salary;
	private String dept;
	
	public Person(String name, int salary, String dept) {
		this.name = name;
		this.salary = salary;
		this.dept = dept;
	}
	
	public String getName() { return name;}
	public int getSalary() { return salary;}
	public String getDepartment() { return dept;}
	
	@Override
	public String toString() {
		return "(" + name + "," + salary + ")";
	}
	
}

