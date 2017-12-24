package com.jsoniter;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.Config;
import com.jsoniter.spi.JsoniterSpi;

public class TestJsoniter {
	private static final Logger log = LoggerFactory.getLogger(TestJsoniter.class);

	@Test
	public void testJsoniter() {
		String jsonStr = "{\"cost\":0.0,\"name\":\"mjson\",\"alias\":[\"json\",\"测试minimal json\"],\"version\":\"1.0\"}";
		Any any = JsonIterator.deserialize(jsonStr);
		String name = any.get("name").toString();
		log.info("name={}", name);
		log.info("alias[1]={}", any.get("alias").get(1).toString());
	}

	@Test
	public void testJsoniter2() {
		Config newConfig = JsoniterSpi.getDefaultConfig().copyBuilder().escapeUnicode(false).build();
		JsoniterSpi.setDefaultConfig(newConfig);
		JsoniterSpi.setCurrentConfig(newConfig);
		JsoniterSpi.registerTypeEncoder(UserBean.class, new UserBeanEncoder());
		UserBean userBean = new UserBean("测试1", "测试2", 222);
		String jsonStr = JsonStream.serialize(userBean);
		log.info("jsonStr={}", jsonStr);
		
		JsoniterSpi.registerTypeDecoder(UserBean.class, new UserBeanDecoder());
		UserBean userBean2=JsonIterator.deserialize(jsonStr,UserBean.class);
		log.info("userBean2={}", userBean2);
	}
}
