package com.basic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.OrderBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestGson {
	private static final Logger log = LoggerFactory.getLogger(TestFastJson.class);

	@Test
	public void testGsonExpose(){
		OrderBean order=new OrderBean();
		order.setGoodsId("God001");
		order.setOrderNo("Ord002");
		order.setPrice(12);
		order.setUserId("User003");
		Gson gson = new Gson();
		String jsonStr=gson.toJson(order);
		log.debug("jsonStr={}",jsonStr);
	    Gson excludeGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	    String jsonStr2=excludeGson.toJson(order);
	    log.debug("jsonStr={}",jsonStr2);
	    
	    OrderBean order2=excludeGson.fromJson(jsonStr, OrderBean.class);
	    log.debug("order={}",order2);
	    
	    OrderBean order3=excludeGson.fromJson(jsonStr2, OrderBean.class);
	    log.debug("order={}",order3);
	}
}
