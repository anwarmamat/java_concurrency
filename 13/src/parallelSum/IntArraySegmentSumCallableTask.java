package parallelSum;

import java.util.concurrent.Callable;

/**
 * 
 * Class of tasks implementing Callable for summing an array segment.
 *
 */
public class IntArraySegmentSumCallableTask implements Callable<Integer>{
	
	private ArraySegment<Integer> segment;
	
	IntArraySegmentSumCallableTask (ArraySegment<Integer> segment) {
		this.segment = segment;
	}

	public Integer call() {
		return SequentialIntArraySegmentSum.sum(segment);
	}
}
