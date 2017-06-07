package io.devbong.learning.rxjava.reactiveprogrammingwithrx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author <a href=“mailto:inomy@coupang.com”>Dante</a>
 * @since 2017. 6. 8.
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Tweet {

	private Long id;
	private String content;
}
