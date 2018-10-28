package com.basic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bean.BigDecimalBean;
import com.bean.MsgBean;
import com.google.gson.Gson;
import com.orika.Name;

public class TestFastJson {
	private static final Logger log = LoggerFactory.getLogger(TestFastJson.class);

	
	@Test
	public void testFastJsonObjectKey() throws Exception {
		Map<BigDecimalBean, String> map=new HashMap<BigDecimalBean, String>();
		BigDecimalBean bean=new BigDecimalBean("testKey", "123.12");
		map.put(bean, "testVal");
		String jsonStr=JSON.toJSONString(map);
		log.info("rtn={}", jsonStr);
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		log.info("jsonobject={}",jsonObj);
//		for (Entry<String, Object> entry : jsonObj.entrySet()) {
//			log.info("jsonobject key={},value={},key type={}", entry.getKey(),entry.getValue(),entry.getKey().getClass());
//		}
		String gsonStr=new Gson().toJson(map);
		log.info("gsonStr={}", gsonStr);
		Map gsonMap = new Gson().fromJson(gsonStr, Map.class);
		log.info("gsonMap={}", gsonMap);
		Set<Map.Entry> set = gsonMap.entrySet();
		for (Map.Entry entry :set) {
			log.info("gsonMap key={},value={},key type={}", entry.getKey(),entry.getValue(),entry.getKey().getClass());
		}
	}
	
	@Test
	public void testFastJsonJsonReference() throws Exception {
		List<Name> nameList = new ArrayList<Name>();
		Name name = Name.random();
		nameList.add(name);
		nameList.add(name);
		String rtn = JSON.toJSONString(nameList);
		log.info("rtn={}", rtn);

		List<Name> nameList2 = JSON.parseArray(rtn, Name.class);
		log.info("rtn={}", nameList2);

		rtn = JSON.toJSONString(nameList, SerializerFeature.DisableCircularReferenceDetect);
		log.info("rtn={}", rtn);

		nameList2 = JSON.parseArray(rtn, Name.class);
		log.info("rtn={}", nameList2);

	}

	@Test
	public void testFastJsonDoubleQuotationMarks() throws Exception {
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
		log.info("rtn={}", jsonStr);// key无引号
		log.info("rtn={}", JSON.toJSON(map));// key有引号
		log.info("rtn={}", JSON.toJSONString(map));

		Map<String, Object> desMap = JSON.parseObject(jsonStr);
		log.info("map={}", desMap);
	}

	@Test
	public void testFastJsonParseObject() throws Exception {
		String jsonStr = "{\"globalId\":123,\"__time__\":345,\"time__\":567}";
		MsgBean msgBean = JSONObject.parseObject(jsonStr, MsgBean.class);
		log.info("rtn={}", msgBean);
		String jsonStr2 = JSONObject.toJSONString(msgBean);
		log.info("rtn={}", jsonStr2);
		msgBean = JSONObject.parseObject(jsonStr2, MsgBean.class);
		log.info("rtn={}", msgBean);
	}

	@Test
	public void testFastJsonJsonObject() {
		//JSONObject顺序问题
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("aaa", 111);
		jsonObject.put("bbb", 222);
		jsonObject.put("ccc", 333);
		jsonObject.put("ddd", 444);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			log.info("key={},value={}", entry.getKey(),entry.getValue());
		}
		
		jsonObject = new JSONObject(true);
		jsonObject.put("aaa", 111);
		jsonObject.put("bbb", 222);
		jsonObject.put("ccc", 333);
		jsonObject.put("ddd", 444);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			log.info("key={},value={}", entry.getKey(),entry.getValue());
		}
	}
}
