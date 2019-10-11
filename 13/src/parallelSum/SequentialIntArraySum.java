package parallelSum;

public class SequentialIntArraySum {
	public static int sum (Integer[] elts) {
		return (SequentialIntArraySegmentSum.sum (new ArraySegment<Integer> (elts, 0, elts.length)));
	}
}
