package io.devbong.learning.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class FirstExample {

	public static void main(String[] args) {

		HelloRxJava helloRxJava = new HelloRxJava();
		helloRxJava.call();
	}

	public static class HelloRxJava {

		public void call() {
			Observable<String> myObservable = Observable.create(
				new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> subscriber) {
						subscriber.onNext("Hello");
						subscriber.onNext("world!");
						subscriber.onCompleted();
					}
				}
			);
			Observer<String> myObserver = new Observer<String>() {
				@Override
				public void onCompleted() {
				}

				@Override
				public void onError(Throwable e) {
				}

				@Override
				public void onNext(String s) {
					System.out.println(s);
				}
			};

			myObservable.subscribe(myObserver);
		}
	}

}