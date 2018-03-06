package com.basic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisStringCommands;

public class TestLettuce {
	private static final Logger log = LoggerFactory.getLogger(TestXmlXXE.class);

	@Test
	public void testLettuceBasicUsage() {
		//RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
		RedisURI redisUri = RedisURI.Builder.redis("localhost").withPort(6379).withDatabase(0).build();
		RedisClient redisClient = RedisClient.create(redisUri);
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisStringCommands<String, String> sync = connection.sync();
		String strVal = "test_value";
		sync.set("test_key_now", strVal);
		String rtnVal = sync.get("test_key_now");
		log.info("rtn={}", rtnVal);
		connection.close();
		redisClient.shutdown();
	}
}
