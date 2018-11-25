package com.groovy;

import org.junit.Test;

import com.basic.TestJavaBasicGrammar;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

public class TestGroovy {

	@Test
	public void testGroovyRunScript(){
		Binding groovyBinding = new Binding();
        groovyBinding.setVariable("testClazz", new TestJavaBasicGrammar());
        GroovyShell groovyShell = new GroovyShell(groovyBinding);
        String scriptContent = "def query = testClazz.testListCopy();\n" +
                "query";
        Script script = groovyShell.parse(scriptContent);
        script.run();
	}
}
