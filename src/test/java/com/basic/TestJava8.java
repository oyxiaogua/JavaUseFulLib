package com.basic;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class TestJava8 {
	private static final Logger log = LoggerFactory.getLogger(TestJava8.class);

	@Test
	public void testFibonacciTailRec() {
		long start = System.currentTimeMillis();
		log.info("rtn={},cost time={}", factorialTailRecu(10), (System.currentTimeMillis() - start));

	}

	@Test
	public void testFibonacciMemoOpt() {
		long start = System.currentTimeMillis();
		log.info("rtn={},cost time={}", fibonacciMemo(10), (System.currentTimeMillis() - start));
	}

	@Test
	public void testJava8DateTime() {
		String str = "Sun Feb 13 15:00:10 2011";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy", Locale.US);
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		log.info("dateTime={}", dateTime);

		formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss xx yyyy", Locale.US);
		String rtnStr = formatter.format(ZonedDateTime.now());
		log.info("rtnStr={}", rtnStr);
	}

	@Test
	public void testStringJoin() {
		List<String> list = Arrays.asList("key1", "key2", null, "key3", "", "key4");
		String rtnStr = list.stream().collect(Collectors.joining(","));
		log.info("rtnStr={}", rtnStr);
		
		//多了最后一个逗号
		StringBuilder sb = new StringBuilder();
		list.forEach(val -> {
			sb.append(val).append(",");
		});
		rtnStr=sb.toString();
		log.info("rtnStr={}", rtnStr);
		
		StringJoiner strJor = new StringJoiner(",");
		for(String str : list) {
		    strJor.add(str);
		}
		rtnStr=strJor.toString();
		log.info("rtnStr={}", rtnStr);
		
		rtnStr = Joiner.on(",").skipNulls().join(list);
		log.info("rtnStr={}", rtnStr);
	}
	
	public static long factorialTailRecu(final long number) {
		return factorialTailRecursion(1, number).invoke();
	}

	/**
	 * 阶乘计算 -- 使用尾递归接口完成
	 * 
	 * @param factorial
	 *            当前递归栈的结果值
	 * @param number
	 *            下一个递归需要计算的值
	 * @return 尾递归接口,调用invoke启动及早求值获得结果
	 */
	public static TailRecursion<Long> factorialTailRecursion(final long factorial, final long number) {
		if (number == 1) {
			return TailInvoke.done(factorial);
		} else {
			return TailInvoke.call(() -> factorialTailRecursion(factorial + number, number - 1));
		}
	}

	/**
	 * 使用统一封装的备忘录模式 对外开放的方法,在内部执行具体的斐波那契策略
	 * {@link #fibonacciCallMemo(Function, Integer)}
	 * 
	 * @param n
	 *            第n个斐波那契数
	 * @return 第n个斐波那契数
	 */
	public static long fibonacciMemo(int n) {
		return callMemo(TestJava8::fibonacciCallMemo, n);
	}

	/**
	 * 私有方法,服务于{@link #fibonacciMemo(int)} ,内部实现为斐波那契算法策略
	 * 
	 * @param fib
	 *            斐波那契算法策略自身,用于递归调用, 在{@link #callMemo(BiFunction, Object)}
	 *            中通过传入this来实例这个策略
	 * @param n
	 *            第n个斐波那契数
	 * @return 第n个斐波那契数
	 */
	private static long fibonacciCallMemo(Function<Integer, Long> fib, Integer n) {
		if (n == 0 || n == 1)
			return 1;
		return fib.apply(n - 1) + fib.apply(n - 2);
	}

	/**
	 * 备忘录模式 函数封装
	 * 
	 * @param function
	 *            递归策略算法
	 * @param input
	 *            输入值
	 * @param <T>
	 *            输出值类型
	 * @param <R>
	 *            返回值类型
	 * @return 将输入值输入递归策略算法，计算出的最终结果
	 */
	public static <T, R> R callMemo(final BiFunction<Function<T, R>, T, R> function, final T input) {
		Function<T, R> memo = new Function<T, R>() {
			private final Map<T, R> cache = new HashMap<>(64);

			public R apply(final T input) {
				return cache.computeIfAbsent(input, key -> function.apply(this, key));
			}
		};
		return memo.apply(input);
	}
}
