package com.rhino;

import org.apache.commons.lang3.StringUtils;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class MyJavaUtils extends ScriptableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getClassName() {
		return "myUtil";
	}

	public String jsFunction_trim(Scriptable param1) {
		return StringUtils.trim(param1.toString());
	}
}
