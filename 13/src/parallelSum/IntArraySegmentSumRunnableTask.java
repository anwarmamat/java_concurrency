package parallelSum;

public class IntArraySegmentSumRunnableTask implements Runnable {
	private ArraySegment<Integer> segment;		// Global element array
	private int results[];						// Results array
	private int resultsPos;						// Where to put result
	
	IntArraySegmentSumRunnableTask (ArraySegment<Integer> segment, int results[], int resultsPos) {
		this.segment = segment;
		this.results = results;
		this.resultsPos = resultsPos;
	}
	
	public void run () {
		results[resultsPos] = SequentialIntArraySegmentSum.sum(segment);
	}
}
