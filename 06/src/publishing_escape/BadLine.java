package publishing_escape;

/**
 * Class of lines, with specification.

 */
public class BadLine {

	/**
	 * @invariant	p1 and p2 must be different points, and non-null
	 */
	private MutablePoint p1;
	private MutablePoint p2;
	
	/**
	 * @param p1	First point defining line
	 * @param p2	Second point
	 * @throws IllegalArgumentException	Thrown if points violate invariant
	 */
	BadLine (MutablePoint p1, MutablePoint p2) throws IllegalArgumentException {
		if ((p1 != null) && (p2 != null) && !p1.equals(p2)) {
			this.p1 = p1;
			this.p2 = p2;
		}
		else {
			throw new IllegalArgumentException (
					"Points to constructor must be non-null and different.");
		}
	}
	
	/**
	 * Getter for first point
	 * 
	 * Precondition / postcondition / exception:  none
	 * @return	first point
	 */
	MutablePoint getP1 () {
		return p1;
	}
	
	/**
	 * Getter for second point
	 * 
	 * Precondition / postcondition / exception:  none
	 * @return	second point
	 */
	MutablePoint getP2 () {
		return p2;
	}
	
	/**
	 * Precondition:  p1 and p2 do not form a vertical line
	 * Postcondition:  returns slope of line formed by p1, p2
	 * Exception:  if precondition is violated, an ArithmeticException is thrown
	 * 
	 * @return	slope, in case precondition holds
	 * @throws ArithmeticException	in case precondition is violated
	 */
	public double slope () throws ArithmeticException {
		return ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
	}

}
