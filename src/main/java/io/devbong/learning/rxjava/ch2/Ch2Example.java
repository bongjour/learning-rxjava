package io.devbong.learning.rxjava.ch2;

import com.google.common.collect.Lists;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class Ch2Example {

	public static void main(String[] args) {
		MakeElements makeElements = new MakeElements();
		makeElements.call();

		FromElements fromElements = new FromElements();
		fromElements.call();
	}

	public static class MakeElements {

		public void call() {

			Observable<Integer> observable = Observable.create(
				new Observable.OnSubscribe<Integer>() {
					@Override
					public void call(Subscriber<? super Integer> observer) {
						for (int i = 0; i < 5; i++) {
							observer.onNext(i);
						}
					}
				}
			);

			observable.subscribe(new MySubscriber());

		}

	}

	public static class FromElements {

		public void call() {
			Observable<Integer> observable = Observable.from(Lists.newArrayList(1, 2, 3, 4));
			observable.subscribe(new MySubscriber());
		}
	}

	public static class MySubscriber implements Observer<Integer> {
		@Override
		public void onCompleted() {
			System.out.println("Observable completed");
		}

		@Override
		public void onError(Throwable e) {
			System.out.println("Error!!!!");
		}

		@Override
		public void onNext(Integer item) {
			if (item == 3) {
				//						throw new IllegalArgumentException();
			}
			System.out.println("Item is " + item);
		}
	}
}
