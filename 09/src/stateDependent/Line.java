package stateDependent;

/**
 * Class of lines, which are defined by two points.
 */
public class Line {

	private Point p1;
	private Point p2;
	
	Line (Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public double slope () {
		return ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX()));
	}
}
