package reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SimpleSubscriber implements Subscriber<Integer> {
	//private Subscription subscription;
	@Override
    public void onSubscribe(Flow.Subscription subscription) {}
	/*
	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("Subscribed");
		this.subscription = subscription;
		this.subscription.request(1); //requesting data from publisher
		System.out.println("onSubscribe requested 1 item");
	}*/
	

    @Override
    public void onNext(Integer item) {
        System.out.println("item = [" + item + "]");
    }

    @Override
    public void onError(Throwable throwable) {}

    @Override
    public void onComplete() {
        System.out.println("complete");
    }
}
