package com.mjson;

import org.junit.Assert;
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

	@Test
	public void testMJsonMerge() {
		// JSON合并
		Json dest = Json.object("name", "John");
		Json src = Json.object("phone", "212-454-3490", "address",
				Json.object("street", "23 4th avenue", "city", "New York"));
		dest.with(src);
		log.info("dest={}", dest);
	}

	@Test
	public void testObjectMerge() {
		Json o1 = Json.object("id", 2, "name", "John", "address",
				Json.object("streetName", "Main", "streetNumber", 20, "city", "Detroit"));
		Json o2 = o1.dup().set("age", 20).at("address").delAt("city").up();
		o1.with(o2, "merge");
		log.info("o1={}", o1);
		Assert.assertTrue(o1.is("age", 20));
		Assert.assertTrue(o1.at("address").is("city", "Detroit"));

		Json a1 = Json.array(1, 2, 20, 30, 50);
		Json a2 = Json.array(0, 2, 20, 30, 35, 40, 51);
		a1.with(a2, "sort");
		log.info("a1={}", a1);
	}
	
	@Test
	public void testMJsonRead() {
		String jsonStr="{\"doc\":{\"marks\":[],\"type\":\"doc\",\"content\":[{\"marks\":[],\"type\":\"discourseContainer\",\"content\":[{\"marks\":[],\"type\":\"paragraph\",\"content\":[{\"text\":\"Here is our first appearance of Joe Dunkin, a prominent character in the story. \",\"marks\":[],\"type\":\"text\",\"attrs\":{}}],\"attrs\":{}},{\"marks\":[],\"type\":\"paragraph\",\"content\":[],\"attrs\":{}}],\"attrs\":{\"hghandle\":\"80177f54-2bb8-4975-be57-b32c04b23c40\",\"title\":\"Chapter 1\",\"owlclass\":\"http://purl.org/spar/doco/Chapter\"}},{\"marks\":[],\"type\":\"discourseContainer\",\"content\":[{\"marks\":[],\"type\":\"paragraph\",\"content\":[{\"text\":\"As the events unfold, Joe visits the newly open Frog Leg restaurant where one finds every unimaginable exotic food, except frog legs of course.\",\"marks\":[],\"type\":\"text\",\"attrs\":{}}],\"attrs\":{}}],\"attrs\":{\"hghandle\":\"c0cd4f1a-38e1-4576-b938-5330da612463\",\"title\":\"Chapter 2\",\"owlclass\":\"http://purl.org/spar/doco/Chapter\"}}],\"attrs\":{\"name\":\"TestSample1\",\"hghandle\":\"5d840cee-5eb0-41fa-899d-fa1239299f26\"}},\"ok\":true}";
		Json json = Json.read(jsonStr);
		boolean rtn=json.at("doc").at("content").at(0).is("type", "discourseContainer");
		Assert.assertTrue(rtn);
	}
}
