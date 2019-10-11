package parallelSum;

import java.util.Random;

/**
 * Class of objects that return random integers in the range [-n, n] inclusive.
 * 
 *
 */
public class SymmetricBoundedRandomInt {
	private Random randomizer;
	private int n;  // Will output random numbers in range [-n, n] inclusive
	
	public SymmetricBoundedRandomInt (int n) {
		this.n = n;
		randomizer = new Random ();
	}
	
	public int next () {
		return (randomizer.nextInt (2*n+1) - n);
	}
}
