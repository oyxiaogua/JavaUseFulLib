package com.fastutil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class TestFastUtil {
	private static final Logger log = LoggerFactory.getLogger(TestFastUtil.class);

	@Test
	public void testFastUtil() {
		Map<String, IntList> subMap = new HashMap<String, IntList>();
		IntList intList = new IntArrayList();
		intList.add(1);
		subMap.put("key_1", intList);
		log.info("map={}",subMap);
	}

}
