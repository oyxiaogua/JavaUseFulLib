package com.basic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.MsgBean;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
	private static final Logger log = LoggerFactory.getLogger(TestJackson.class);

	@Test
	public void testJacksonParseObject() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonStr = "{\"globalId\":123,\"__time__\":345,\"time__\":567}";
		MsgBean msgBean = objectMapper.readValue(jsonStr, MsgBean.class);
		log.info("rtn={}",msgBean);
	}
}
