package reactive;

import java.util.concurrent.Flow;

public class Reactive {

	public static void main(String[] args) {
		SimpleSubscriber subs = new SimpleSubscriber();
		new SimplePublisher(10).subscribe(subs);
	}

}
