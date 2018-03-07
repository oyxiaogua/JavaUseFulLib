package com.basic;

import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.support.ConnectionPoolSupport;

public class TestLettuce {
	private static final Logger log = LoggerFactory.getLogger(TestXmlXXE.class);

	@Test
	public void testLettuceBasicUsage() {
		// RedisClient redisClient =
		// RedisClient.create("redis://localhost:6379/0");
		RedisURI redisUri = getConfigRedisURI();
		RedisClient redisClient = RedisClient.create(redisUri);
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisStringCommands<String, String> sync = connection.sync();

		String strVal = "test_value";
		sync.set("test_key_now", strVal);
		String rtnVal = sync.get("test_key_now");
		log.info("rtn={}", rtnVal);

		RedisCommands<String, String> commSync = connection.sync();
		JSONObject object = new JSONObject();
		object.put("id", 0.01);
		object.put("money", 0.01);
		String listNameStr="testList";
		commSync.lpush(listNameStr, object.toJSONString());

		String luaScriptStr="return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}";
		List<String>rtnList=commSync.eval(luaScriptStr, ScriptOutputType.MULTI,new String[]{"key1","key_2"},"value_3","value_4");
		log.info("lua rtn={}", rtnList);
		
		luaScriptStr="return redis.call('set',KEYS[1],KEYS[2])";
		rtnVal = commSync.eval(luaScriptStr, ScriptOutputType.STATUS, new String[]{"key1","value_2"});
		log.info("lua set rtn={}", rtnVal);
		
		luaScriptStr="return redis.call('get',KEYS[1])";
		rtnList=commSync.eval(luaScriptStr, ScriptOutputType.MULTI,new String[]{"key1"});
		log.info("lua get rtn={}", rtnList);
		
		rtnVal=commSync.rpop(listNameStr);
		log.info("rtn={}", rtnVal);
		
		RedisAsyncCommands<String, String> async = connection.async();
		async.lpush(listNameStr, object.toJSONString());
		RedisFuture<String> futureRtn = async.rpop(listNameStr);
		futureRtn.thenAccept((e) -> log.info("rtn={}",e));
		connection.close();
		redisClient.shutdown();
	}

	
	@Test
	public void testLettuceConnectionPool() throws Exception {
		RedisURI redisUri = getConfigRedisURI();
		RedisClient redisClient = RedisClient.create(redisUri);
		GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport
	               .createGenericObjectPool(() -> redisClient.connect(), new GenericObjectPoolConfig());
		try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
		    RedisCommands<String, String> commands = connection.sync();
		    commands.flushdb();
		    
		    commands.multi();
		    commands.set("test_key_i_1","1");
		    commands.set("test_key_2", "value2");
		    commands.incr("test_key_i_1");
		    commands.exec();
		    
		    String rtnVal = commands.get("test_key_i_1");
			log.info("rtn={}", rtnVal);
		}
		pool.close();
		redisClient.shutdown();
	}
	
	/**
	 * 获取初始化连接URI
	 * @return
	 */
	private RedisURI getConfigRedisURI() {
		RedisURI redisUri = RedisURI.Builder.redis("localhost").withPort(6379).withDatabase(0).build();
		return redisUri;
	}
}
