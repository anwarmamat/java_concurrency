package publishing_escape;

public class Outer {
	private int a = 100;
	public void foo () { System.out.println ("Outer a = " + a); }
	
	//public static class Inner {
	public class Inner {	
		private int b=1;// = a + 1;
		public void foo () { System.out.println ("Inner b = " + b); }
	}
}
