package com.javatest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestStringUtils {
	private static final Logger log = LoggerFactory.getLogger(TestStringUtils.class);

	@Test
	public void testApacheCommonStringUtils() {
		String str = "test 测试 for测试testForT测est";
		String replaceOneStr = StringUtils.replaceOnce(str, "test", "替换");
		log.info("result={}", replaceOneStr);
		String replaceAll = StringUtils.replace(str, "test", "替换");
		log.info("result={}", replaceAll);
	}
}
