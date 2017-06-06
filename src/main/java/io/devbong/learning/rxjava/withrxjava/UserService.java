package io.devbong.learning.rxjava.withrxjava;

import java.util.Arrays;
import java.util.List;

public class UserService {

	public List<User> findUsers() {
		return Arrays.asList(
			User.of("dante", 10),
			User.of("dennis", 3),
			User.of("pudding", 2),
			User.of("wilson", 5),
			User.of("tejas", 7),
			User.of("kil", 4),
			User.of("billy", 10),
			User.of("vince", 7)
		);
	}

	public static class User {

		private String name;
		private Integer level;

		public static User of(String name, Integer level) {
			User user = new User();
			user.name = name;
			user.level = level;
			return user;
		}

		public String getName() {
			return name;
		}

		public Integer getLevel() {
			return level;
		}

	}
}
