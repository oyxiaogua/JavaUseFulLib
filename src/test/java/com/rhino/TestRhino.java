package com.rhino;

import java.io.File;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRhino {
	private static final Logger log = LoggerFactory.getLogger(TestRhino.class);

	@Test
	public void testRhinoRunSimpleJsCode() throws Exception {
		Context ctx = Context.enter();
		Scriptable scope = ctx.initStandardObjects();
		String jsStr = "(function(){return \"hello,world你好\"})()";
		String result = (String) ctx.evaluateString(scope, jsStr, null, 0, null);
		log.info("rtn={}", result);

		Object jsOut = Context.javaToJS(log, scope);
		ScriptableObject.putProperty(scope, "log", jsOut);

		ScriptableObject.defineClass(scope, MyJavaUtils.class);
		MyJavaUtils myUtil = (MyJavaUtils) ctx.newObject(scope, "myUtil");
		scope.put("myUtil", scope, myUtil);
		jsStr = "log.info(myUtil.trim(\"   测试   \"));";
		ctx.evaluateString(scope, jsStr, null, 0, null);

		jsStr = "9*(1+2)";
		Object objRtn = ctx.evaluateString(scope, jsStr, null, 0, null);
		double dbRtn = Context.toNumber(objRtn);
		log.info("rtn={}", dbRtn);

		jsStr = "function helloFunc(v){ return v;}";
		ctx.evaluateString(scope, jsStr, "helloFunc", 0, null);
		Function helloFunc = (Function) scope.get("helloFunc", scope);
		Object call = helloFunc.call(Context.getCurrentContext(), scope, helloFunc, new Object[] { "test测试" });
		log.info("rtn={}", call.toString());

		// 加载jquery
		jsStr = "var print = function(v){log.debug(\"v\");return v;}";
		ctx.setOptimizationLevel(-1);
		ctx.evaluateString(scope, jsStr, "print", 0, null);
		String jqueryStr = FileUtils.readFileToString(new File("src/test/resources/env.rhino.1.2.js"));
		ctx.evaluateString(scope, jqueryStr, "env.rhino.1.2.js", 0, null);

		jqueryStr = FileUtils.readFileToString(new File("src/test/resources/jquery.min.js"));
		ctx.evaluateString(scope, jqueryStr, "jquery.min.js", 0, null);

		jsStr = "function getJsonStr(){var aObj={\"key_1\":\"测试\",subArr:[\"测试2\",\"test3\"]}; return JSON.stringify(aObj);}";
		ctx.evaluateString(scope, jsStr, "getJsonStr", 0, null);
		helloFunc = (Function) scope.get("getJsonStr", scope);
		call = helloFunc.call(Context.getCurrentContext(), scope, helloFunc, new Object[] {});
		log.info("rtn={}", call.toString());
		Context.exit();
	}

	@Test
	public void testNashornRunSimpleJsCode() throws Exception {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		String jsStr = "function hello(v){return \"hello,\"+v}";
		nashorn.eval(jsStr);
		Invocable invocable = (Invocable) nashorn;
		Object result = invocable.invokeFunction("hello", new Object[] { "测试" });
		log.info("rtn={}", result.toString());

		jsStr = "var stringUtils = Java.type(\"org.apache.commons.lang3.StringUtils\");function myTrim(v){return stringUtils.trim(v);}";
		nashorn.eval(jsStr);
		invocable = (Invocable) nashorn;
		result = invocable.invokeFunction("myTrim", new Object[] { "   测试   " });
		log.info("rtn={}", result.toString());
	}
}
