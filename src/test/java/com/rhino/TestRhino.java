package com.rhino;

import org.junit.Test;
import org.mozilla.javascript.Context;
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
		Context.exit();
	}
}
