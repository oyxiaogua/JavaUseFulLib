package com.basic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJavaBasicGrammar {
	private static final Logger log = LoggerFactory.getLogger(TestJava8.class);

	@Test
	public void testDateFormat() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
		String dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
		df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
		dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
	}
}
