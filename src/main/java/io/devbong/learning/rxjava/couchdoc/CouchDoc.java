package io.devbong.learning.rxjava.couchdoc;

import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CouchDoc {

	public static void main(String[] args) throws InterruptedException {

		// consuming observables
		Observable
			.just(1, 2, 3)
			.subscribe(new IntSubscriber());

		// Exception
		Observable
			.just(1, 2, 3)
			.doOnNext(i -> {
				if (i.equals(2)) {
					throw new RuntimeException(("I don't like 2"));
				}
			})
			.subscribe(new IntSubscriber());

		// take
		Observable
			.just("The", "Dave", "Brubeck", "Quartet", "Time", "Out")
			.take(5)
			.subscribe(new StringSubscriber());

		Observable
			.just(1, 2, 3)
			.subscribe(System.out::println);

		// From async to sync
		// 결과가 안찍힌다. 다른 쓰레드에서 실행됨.
		Observable
			.interval(1, TimeUnit.SECONDS)
			.subscribe(num -> {
				System.out.println("Got : " + num);
			});

		CountDownLatch latch = new CountDownLatch(5);

		Observable
			.interval(1, TimeUnit.MILLISECONDS)
			.subscribe(num -> {
				latch.countDown();
				System.out.println("Get : " + num);
			});

		latch.await();

		BlockingObservable<Long> observable = Observable
			.interval(1, TimeUnit.MILLISECONDS)
			.take(10)
			.toBlocking();

		observable.forEach(counter -> {
			System.out.println("Got1 : " + counter);
		});

		Integer single = Observable
			.just(1)
			.toBlocking()
			.single();

		List<Integer> list = Observable
			.just(1, 2, 3)
			.toList()
			.toBlocking()
			.single();

		System.out.println(list);

		// Creating Observable
		Observable<Integer> createdObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				try {
					if (!subscriber.isUnsubscribed()) {
						for (int i = 0; i < 5; i++) {
							subscriber.onNext(i);
						}

						subscriber.onCompleted();
					}
				} catch (Exception ex) {
					subscriber.onError(ex);
				}
			}
		});

		createdObservable.subscribe(integer -> {
			System.out.println("CreatedObservable : " + integer);
		});

		// Transforming Observables
		Observable
			.interval(10, TimeUnit.MILLISECONDS)
			.take(20)
			.map(input -> {
				if (input % 3 == 0) {
					return "Fizz";
				} else if (input % 5 == 0) {
					return "Buzz";
				}
				return Long.toString(input);
			})
			.toBlocking()
			.forEach(System.out::println);

		// scan
		Observable
			.just(1, 2, 3, 4, 5)
			.scan((sum, value) -> sum + value)
			.subscribe(integer -> System.out.println("Sum : " + integer));

		// groupBy


	}

	public static class StringSubscriber extends Subscriber<String> {
		@Override
		public void onCompleted() {
			System.out.println("Completed Observable.");
		}

		@Override
		public void onError(Throwable e) {
			System.out.println("Whooops : " + e.getMessage());
		}

		@Override
		public void onNext(String name) {
			System.out.println("Got : " + name);
		}
	}

	public static class IntSubscriber extends Subscriber<Integer> {
		@Override
		public void onCompleted() {
			System.out.println("Completed Observable.");
		}

		@Override
		public void onError(Throwable e) {
			System.out.println("Whooops : " + e.getMessage());
		}

		@Override
		public void onNext(Integer integer) {
			System.out.println("Got : " + integer);
		}
	}
}
