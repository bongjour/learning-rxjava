package io.devbong.learning.rxjava.reactiveprogrammingwithrx;

/**
 *
 * @author <a href=“mailto:inomy@coupang.com”>Dante</a>
 * @since 2017. 6. 8.
 */
public class RxUtils {

	public static void log(Object msg) {
		System.out.println(Thread.currentThread().getName() + " : " + msg);
	}

	public static void sleep(int duringMs) {
		try {
			Thread.sleep(duringMs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
