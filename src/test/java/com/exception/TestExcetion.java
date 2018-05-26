package com.exception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExcetion {
	private static final Logger log = LoggerFactory.getLogger(TestExcetion.class);

	@Test
	public void testErrorStatusException() {
		try {
			throw new ErrorStatusException("000001", "空指针异常");
		} catch (Exception e) {
			log.error("testErrorStatusException error", e);
		}
	}
}
