package io.devbong.learning.rxjava.reactiveprogrammingwithrx.ch2;

import io.devbong.learning.rxjava.reactiveprogrammingwithrx.RxUtils;
import rx.Observable;

/**
 *
 * @author <a href=“mailto:inomy@coupang.com”>Dante</a>
 * @since 2017. 6. 8.
 */

public class MakeObservable2 {

	public static void main(String[] args) {

		delayed(10).subscribe(RxUtils::log);


	}

	static <T> Observable<T> delayed(T x) {
		return Observable.create(subscriber -> {
			Runnable r = () -> {
				RxUtils.sleep(1000);
				if (!subscriber.isUnsubscribed()) {
					subscriber.onNext(x);
					subscriber.onCompleted();
				}
			};
			new Thread(r).start();
		});
	}
}
