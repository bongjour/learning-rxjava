package io.devbong.learning.rxjava.reactiveprogrammingwithrx.ch2;

import com.google.common.collect.Lists;
import io.devbong.learning.rxjava.reactiveprogrammingwithrx.RxUtils;
import io.devbong.learning.rxjava.reactiveprogrammingwithrx.Tweet;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

import java.math.BigInteger;
import java.util.List;

import static io.devbong.learning.rxjava.reactiveprogrammingwithrx.RxUtils.log;

/**
 *
 * @author <a href=“mailto:inomy@coupang.com”>Dante</a>
 * @since 2017. 6. 8.
 */

public class MakeObservable1 {

	public static void main(String[] args) throws InterruptedException {

		List<Tweet> tweets = createTweets(10);

		Observable<Tweet> observable = Observable.from(tweets);

		//		observable.takeUntil(tweet -> tweet.getContent().contains("6"));

		/**
		 * 1. observer
		 * 2. subscriber : Observer의 추상 구현체
		 */

		observable.subscribe(new Observer1());
		observable.subscribe(new Subscriber1());

		/**
		 * observable 만들기
		 */
		Observable.just(new Tweet(1L, "aaa"));
		Observable.just(new Tweet(1L, "aaa"), new Tweet(2L, "bbb")); // 9개까지
		Observable.from(tweets); // Future를 넘길수 있다. timeout등을 설정 가능
		Observable.range(1, 100).subscribe(System.out::println);
		Observable.empty();
		Observable.never(); // 주로 테스트 용도도
		Observable.error(new IllegalArgumentException());

		/**
		 * Observable.create()
		 */

		log("Before");
		Observable
			.range(5, 3)
			.subscribe(RxUtils::log);
		log("After");

		Observable<Integer> ints = Observable
			.create(new Observable.OnSubscribe<Integer>() {
				@Override
				public void call(Subscriber<? super Integer> subscriber) {
					log("Create");
					subscriber.onNext(5);
					subscriber.onNext(6);
					subscriber.onNext(7);
					subscriber.onCompleted();
					log("Completed");
				}
			});

		log("Starting");
		ints.subscribe(i -> log("Element :" + i));
		log("Exit");

		/**
		 * 구독할 때 마다 실행된다.
		 */
		Observable<Integer> ints1 = Observable
			.create(new Observable.OnSubscribe<Integer>() {
				@Override
				public void call(Subscriber<? super Integer> subscriber) {
					log("Create");
					subscriber.onNext(54);
					subscriber.onCompleted();
					log("Complete");
				}
			});

		log("Starting");
		ints1.subscribe(i -> log("Element A : " + i));
		ints1.subscribe(i -> log("Element B : " + i));
		log("Exit");

		Observable<Integer> ints2 = Observable
			.create(new Observable.OnSubscribe<Integer>() {
				@Override
				public void call(Subscriber<? super Integer> subscriber) {
					log("Create");
					subscriber.onNext(54);
					subscriber.onCompleted();
					log("Complete");
				}
			}).cache();

		log("Starting 2");
		ints2.subscribe(i -> log("Element A : " + i));
		ints2.subscribe(i -> log("Element B : " + i));
		log("Exit 2");

		/**
		 * 무한 스트림
		 */

		Observable<BigInteger> bigIntegerObservable = Observable.create(new Observable.OnSubscribe<BigInteger>() {
			BigInteger i = BigInteger.ZERO;

			@Override public void call(Subscriber<? super BigInteger> subscriber) {
				while (true) { // 이렇게 구현하지 말자.
					subscriber.onNext(i);
					i = i.add(BigInteger.ONE);
				}
			}
		});

		//		bigIntegerObservable.subscribe(i -> log("Item : " + i));

		Observable<BigInteger> bigIntegerObservable2 = Observable.create(new Observable.OnSubscribe<BigInteger>() {
			BigInteger i = BigInteger.ZERO;
			@Override
			public void call(Subscriber<? super BigInteger> subscriber) {
				Runnable r = () -> {
					while (!subscriber.isUnsubscribed()) { // 이렇게 구현하지 말자.
						subscriber.onNext(i);
						i = i.add(BigInteger.ONE);
					}
				};

				new Thread(r).start();
			}
		});

		Subscription subscription = bigIntegerObservable2.subscribe(i -> log("Item : " + i));

		Thread.sleep(200);

		subscription.unsubscribe();

		log("Done");


	}

	private static List<Tweet> createTweets(int until) {
		List<Tweet> tweets = Lists.newArrayList();
		for (int i = 0; i < until; i++) {
			tweets.add(new Tweet((long) i, "aaa" + i));
		}

		return tweets;
	}

	private static class Subscriber1 extends Subscriber<Tweet> {
		@Override
		public void onCompleted() {
			System.out.println("subscriber done");
		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onNext(Tweet tweet) {
			if (tweet.getContent().contains("a") && !isUnsubscribed()) {
				unsubscribe();
			}

			System.out.println(tweet);
		}
	}

	private static class Observer1 implements Observer<Tweet> {
		@Override
		public void onCompleted() {
			System.out.println("DONE");
		}

		@Override
		public void onError(Throwable e) {
			e.printStackTrace();
		}

		@Override
		public void onNext(Tweet tweet) {
			System.out.println(tweet);
		}
	}

}


