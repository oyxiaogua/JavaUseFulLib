package com.mjson;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mjson.Json;

public class TestMjson {
	private static final Logger log = LoggerFactory.getLogger(TestMjson.class);

	@Test
	public void testMJson_Demo() {
		// 擅长处理格式不固定的 Json 数据
		Json x = Json.object().set("name", "mjson").set("version", "1.0").set("cost", 0.0).set("alias",
				Json.array("json", "minimal json"));
		log.info("json has key_1={}", x.has("key_1"));
		String rtnStr = x.at("name").asString();
		log.info("name={}", rtnStr);
		Json json = x.at("alias").at(1);
		log.info("alias={}", json.toString());
		double cost = x.at("alias").up().at("cost").asDouble();
		log.info("cost={}", cost);
		String s = x.toString();
		boolean check = x.equals(Json.read(s));
		log.info("equals={}", check);
	}
}
