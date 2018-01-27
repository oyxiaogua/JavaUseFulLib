package com.rhino;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRhino {
	private static final Logger log = LoggerFactory.getLogger(TestRhino.class);

	@Test
	public void testRhinoRunSimpleJsCode() {
		Context ctx = Context.enter();
		Scriptable scope = ctx.initStandardObjects();
		String jsStr = "(function(){return \"hello,world你好\"})()";
		String result = (String) ctx.evaluateString(scope, jsStr, null, 0, null);
		log.info("rtn={}", result);
		Context.exit();
	}
}
