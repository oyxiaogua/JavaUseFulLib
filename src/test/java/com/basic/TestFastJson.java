package com.basic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dfargx.TestDfargx;

public class TestFastJson {
	private static final Logger log = LoggerFactory.getLogger(TestDfargx.class);

	@Test
	public void testFastJsonJson2() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("key_1", "value_1\u0001\u0080\u0002");
		map.put("A", aMap);
		map.put("reportDate", "20180902");
		SerializeWriter out = new SerializeWriter(
				new SerializerFeature[] { SerializerFeature.DisableCircularReferenceDetect });
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.write(map);
		String jsonStr = out.toString();
		log.info("rtn={}", jsonStr);//key无引号
		log.info("rtn={}", JSON.toJSON(map));//key有引号
		log.info("rtn={}", JSON.toJSONString(map));
		
		Map<String, Object> desMap =JSON.parseObject(jsonStr);
		log.info("map={}", desMap);
	}
}
