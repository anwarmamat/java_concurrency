package colored_line;

/**
 * Thread-safe colored line

 */
public class ColoredMutableLine {

	// Invariant:  p1 and p2 must be different points.
	
	private Object InvLockP1P2 = new Object ();
	
	private Point p1;  // Guarded by lock LockP1P2Inv
	private Point p2;  // Guarded by lock LockP1P2Inv
	
	// Invariant:  color equals last call to setColor, or to constructor argument, whichever is most recent
	
	private Object InvLockColor = new Object ();
	
	private int color; // Guarded by lock LockColor

	/**
	 * Constructor CorrectLine throws an exception if points
	 * overlap, or if one is null.
	 * 
	 * @param p1  First point
	 * @param p2  Second point
	 * @param color  Line color
	 * @throws IllegalArgumentException  Thrown if points are problematic
	 */
	ColoredMutableLine (Point p1, Point p2, int color) throws IllegalArgumentException {
		if ((p1 != null) && (p2 != null) && !p1.equals(p2)) {
			this.p1 = p1;
			this.p2 = p2;
			this.color = color;
		}
		else {
			throw new IllegalArgumentException (
					"Points to ColoredMutableLine Constructor must be different:  " +
					p1.toString() + "given twice.");
		}
	}
	
	public Point getP1() {
		synchronized (InvLockP1P2) { return p1; }
	}

	/**
	 * Change first point.
	 * 
	 * Precondition:  Argument p1 is non-null, not equal to field p2
	 * Postcondition:  Field p1 is updated
	 * Exception:  IllegalArgumentException thrown
	 * 
	 * @param p1	New value for field p1.
	 */
	public void setP1(Point p1) {
		synchronized (InvLockP1P2) {
			if ((p1 != null) && !p2.equals(p1))
				this.p1 = p1;
			else throw new IllegalArgumentException (
					"Illegal argument to setP1 : " +
					p1.toString() +
					" same as second point");
		}
	}


	public Point getP2() {
		synchronized (InvLockP1P2) { return p2; }
	}

	/**
	 * Change second point.
	 * 
	 * Precondition:  Argument p2 is non-null, not equal to field p1
	 * Postcondition:  Field p2 is updated
	 * Exception:  IllegalArgumentException thrown
	 * 
	 * @param p2	New value for field p2.
	 */
	public void setP2(Point p2) {
		synchronized (InvLockP1P2) {
			if ((p2 != null) && !p1.equals(p2))
				this.p2 = p2;
			else throw new IllegalArgumentException (
					"Illegal argument to setP2 : " +
					p2.toString() +
					" same as first point");
		}
	}

	public int getColor() {
		synchronized (InvLockColor) { return color; }
	}

	/**
	 * Change color.
	 * 
	 * Precondition:  none
	 * Postcondition:  color is set to parameter
	 * Exception:  none
	 * 
	 * @param color		New color for line
	 */
	public void setColor(int color) {
		synchronized (InvLockColor) { this.color = color; }
	}
	
	/**
	 * slope returns the slope of the line, if it is well-defined.
	 * 
	 * Precondition:  p1 and p2 do not form a vertical line
	 * Postcondition:  returns slope of line formed by p1, p2
	 * Exception:  throws ArithmeticException if precondition violated
	 * @return	slope, if defined
	 * @throws ArithmeticException if slope not defined
	 */
	public double slope () throws ArithmeticException {
		synchronized (InvLockP1P2) {
			return ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
		}
	}

}
