package com.basic;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.AbstractFieldBean;
import com.bean.BigDecimalBean;
import com.bean.JacksonGetBean;
import com.bean.MsgBean;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
	private static final Logger log = LoggerFactory.getLogger(TestJackson.class);
	@Test
	public void testJacksonAbstractField() throws Exception{
		AbstractFieldBean bean=new AbstractFieldBean(2);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(bean);
		log.info("json={}", jsonStr);
		AbstractFieldBean b1 = mapper.readValue(jsonStr, AbstractFieldBean.class);
		log.info("bean={}", b1);
		
		JsonFactory factory = new JsonFactory();
		factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
		mapper = new ObjectMapper(factory);
		jsonStr=jsonStr.replace("\"", "'");
		log.info("json={}", jsonStr);
		b1 = mapper.readValue(jsonStr, AbstractFieldBean.class);
		log.info("bean={}", b1);
	}
	
	@Test
	public void testJacksonUseBigDecimalForFloats() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		BigDecimalBean bean = new BigDecimalBean("test_1", "2.00");
		String jsonStr = mapper.writeValueAsString(bean);
		BigDecimalBean b1 = mapper.readValue(jsonStr, BigDecimalBean.class);
		log.info("json={},bean={}", jsonStr, b1);
		mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		b1 = mapper.readValue(jsonStr, BigDecimalBean.class);
		log.info("json={},bean={}", jsonStr, b1);
	}
	
	@Test
	public void testJacksonParseObject() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String jsonStr = "{\"globalId\":123,\"__time__\":345,\"time__\":567}";
		MsgBean msgBean = objectMapper.readValue(jsonStr, MsgBean.class);
		log.info("rtn={}",msgBean);
	}
	
	
	@Test
	public void testJacksonJsonGetSet() throws Exception {
		JacksonGetBean bean=new JacksonGetBean();
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("key_date", new Date());
		dataMap.put("key_int", 1000);
		dataMap.put("key_bigdecimal", new BigDecimal("123456789.98766541"));
		bean.setId(11111L);
		bean.setDataMap(dataMap);
		
		ObjectMapper objectMapper = new ObjectMapper();
		//objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH));
		String jsonStr=objectMapper.writeValueAsString(bean);
		log.info("rtn={}",jsonStr);
		
		JacksonGetBean bean2=objectMapper.readValue(jsonStr, JacksonGetBean.class);
		log.info("rtn={}",bean2);
	}
}
