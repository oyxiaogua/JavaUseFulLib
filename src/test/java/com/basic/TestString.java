package com.basic;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestString {
	private static final Logger log = LoggerFactory.getLogger(TestXmlXXE.class);

	@Test
	public void testHashCodeEqZero(){
		String str="f5a5a608";
		Assert.assertEquals(0, str.hashCode());
		log.debug("str={} hashcode=0",str);
	}
}
