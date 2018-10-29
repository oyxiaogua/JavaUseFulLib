package com.basic;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.StaticObjectBean;
import com.bean.StaticStringBean;

public class TestString {
	private static final Logger log = LoggerFactory.getLogger(TestXmlXXE.class);

	@Test
	public void testStaticStringField(){
		log.info("A={}",StaticObjectBean.A);
		log.info("B={}",StaticObjectBean.B);
		log.info("GetB={}",StaticObjectBean.getB());
		
		log.info("A={}",StaticStringBean.A);
		log.info("B={}",StaticStringBean.B);
		log.info("GetB={}",StaticStringBean.getB());
	}
	
	@Test
	public void testStringReplace(){
		String s = "(1)(11)(111)(1)";
		log.info("rtn={}",s.replace("(1)", "(2)"));
		log.info("rtn={}",s.replaceAll("(1)", "(2)"));
	}
	
	@Test
	public void testStringConstantFolding(){
		//常量折叠 编译期常量加减乘除的运算过程会被折叠
		String a = "hello2";
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        Assert.assertTrue((a == c));
        Assert.assertFalse((a == e));
	}
	
	@Test
	public void testObjectsToString(){
		String str=null;
		log.info("str={}",java.util.Objects.toString(str, "default"));
	}
	@Test
	public void testHashCodeEqZero(){
		String str="f5a5a608";
		Assert.assertEquals(0, str.hashCode());
		log.debug("str={} hashcode=0",str);
	}
}
