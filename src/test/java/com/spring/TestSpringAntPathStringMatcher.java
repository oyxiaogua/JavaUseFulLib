package com.spring;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

public class TestSpringAntPathStringMatcher {
	private static final Logger log = LoggerFactory.getLogger(TestSpringAntPathStringMatcher.class);

	@Test
	public void testSpringAntPathMatcher() {
		log.debug("testSpringAntPathMatcher");
		AntPathMatcher matcher = new AntPathMatcher();
		String requestPath = "/product/{\"name\":\"hello\", \"price\":3.1}";
		String patternPath = "/product/{productJson:.+}";
		boolean result = matcher.match(patternPath, requestPath);
		Assert.assertTrue(result);
	}
}
