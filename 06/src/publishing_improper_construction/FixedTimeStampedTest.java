package publishing_improper_construction;

import static org.junit.Assert.*;

import org.junit.Test;

//This test counts the number of times that a FixedTimeStampedObj object incorrectly
//lets "this" escape.

public class FixedTimeStampedTest {

	@Test
	public void test() {
		int errorCount = 0;
		int iterations = 10000;
		Thread t1;

		for (int i=0; i<iterations; i++) {
			t1 = new Thread(new Runnable() {
				public void run() {
					FixedTimeStampedObj.newInstance(new Object());;
				}
			});
			t1.start();

			if (TimeStampedObjCache.lastObjCreated.getTimeStamp() == null) {
				errorCount++;
			}
		}

		System.out.printf("Error count = %d",errorCount);
		assertTrue(errorCount == 0);

	}

}
