package com.hutool;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class TestHuTool {
	private static final Logger log = LoggerFactory.getLogger(TestHuTool.class);
    
	@Test
	public void testConvertXmlToJson(){
		String xmlStr="<?xml version=\"1.0\" encoding=\"utf-8\"?><bookstore>	<book category=\"COOKING\">		<title lang=\"en\">Everyday Italian</title>		<year id=\"1001\">2005</year>		<price>30.00</price>	</book></bookstore>";
		JSONObject jsonObj=JSONUtil.xmlToJson(xmlStr);
		String jsonStr=JSONUtil.toJsonPrettyStr(jsonObj);
		log.debug("json={}",jsonStr);
	}
	
}
