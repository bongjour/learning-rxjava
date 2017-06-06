package io.devbong.learning.rxjava.withrxjava;

import io.devbong.learning.rxjava.withrxjava.UserService.User;
import rx.Observable;
import rx.observables.BlockingObservable;
import rx.schedulers.Schedulers;

import java.util.List;

public class CompositionIllustration {

	public static void main(String[] args) {

		UserService userService = new UserService();
		List<User> users = userService.findUsers();

		BlockingObservable<List<User>> observable = Observable.from(users)
			//			.filter(user -> user.getLevel().equals(10))
			.toSortedList((user1, user2) -> user1.getLevel().compareTo(user2.getLevel()))
			.subscribeOn(Schedulers.io())
			.doOnCompleted(() -> System.out.println("done"))
			.toBlocking();

		observable.subscribe(userList -> {
			userList.forEach(user-> {
				System.out.println(Thread.currentThread().getName() + " " + user.getName() + " : " + user.getName());
			});
		});

	}
}
