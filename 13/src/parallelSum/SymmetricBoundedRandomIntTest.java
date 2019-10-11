package parallelSum;

import static org.junit.Assert.*;

import org.junit.Test;

public class SymmetricBoundedRandomIntTest {

	@Test
	public void test() {
		int n = 5;
		int i = 0;
		SymmetricBoundedRandomInt r = new SymmetricBoundedRandomInt (n);
		for (;;) {
			i = r.next();
			assert((i <= n) && (i >= (-1 * n)));
			System.out.println(i);
			if (i == n) break;
			if (i == -1*n) break;
		}
	}

}
