package io.devbong.learning.rxjava.reactiveprogrammingwithrx.ch6;

import rx.Observable;

/**
 *
 * @author <a href=“mailto:inomy@coupang.com”>Dante</a>
 * @since 2017. 5. 29.
 */
public class Ch6 {

	public static void main(String[] args) {

		Observable<Integer> integerObservable = Observable
			.range(1, 7)
			.buffer(1, 2)
			.flatMapIterable(list -> list);

		integerObservable.subscribe(System.out::println);

	}


}
