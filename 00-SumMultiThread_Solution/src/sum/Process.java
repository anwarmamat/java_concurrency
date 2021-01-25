package sum;

public class Process {
	public static double apply(double num) {
		double s = Math.sqrt(1.0/ (num+0.5)); 
		s = Math.pow(s,s);
		return s;
	}
}
