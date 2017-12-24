package com.dfargx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.yatt.dfargx.RegexMatcher;
import top.yatt.dfargx.RegexSearcher;

public class TestDfargx {
	private static final Logger log = LoggerFactory.getLogger(TestDfargx.class);
	private static final String str = "^(a+)+$";
	private static final String paraStr = "aaaaaaaaaaaaaaaaaaaaaaaaaaz";

	@Test
	public void testJavaRegexProblem() {
		// 匹配器逐步回退，并尝试所有的组合以找出匹配符号
		long startTime = System.currentTimeMillis();
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(paraStr);
		matcher.find();
		log.info("str length={},cost time={}", str.length(), (System.currentTimeMillis() - startTime));
	}

	@Test
	public void testFixedJavaRegexProblemWithTimeOut() {
		long startTime = System.currentTimeMillis();
		try {
			Matcher matcher = RegularExpressionWithTimeOut.createMatcherWithTimeout(paraStr, str, 2000);
			matcher.find();
		} catch (Exception e) {
			log.error("regex timeout");
		} finally {
			log.info("str length={},cost time={}", str.length(), (System.currentTimeMillis() - startTime));
		}
	}

	@Test
	public void testFixedJavaRegexProblemWithDfargx() {
		long startTime = System.currentTimeMillis();
		RegexMatcher matcher = new RegexMatcher(str);
		boolean result = matcher.match(paraStr);
		log.info("str length={},result={},cost time={}", str.length(), result,
				(System.currentTimeMillis() - startTime));
	}

	@Test
	public void testDfargxMatchText() {
		String str = "(a+)+";
		String paraStr = "aazabcasza";
		RegexSearcher search = new RegexSearcher(str);
		search.search(paraStr);
		while (search.hasMoreElements()) {
			log.info("v={}", search.nextElement());
		}
	}

}
