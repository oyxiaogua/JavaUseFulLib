package com.janino;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.lang.model.element.Modifier;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.janino.SimpleCompiler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TestJanino {
	private static final Logger log = LoggerFactory.getLogger(TestJanino.class);

	@Test
	public void testJaninoParseJavaExpression() throws Exception {
		IExpressionEvaluator ee = CompilerFactoryFactory.getDefaultCompilerFactory().newExpressionEvaluator();
		ee.setExpressionType(double.class);
		ee.setParameters(new String[] { "total" }, new Class[] { double.class });
		ee.cook("total >= 100.0 ? 0 : 1.2");
		Object[] arguments = { new Double("300") };
		Object result = ee.evaluate(arguments);
		log.info("result={}", result);
	}

	@Test
	public void testJaninoParseJavaCode() throws Exception {
		SimpleCompiler compiler = new SimpleCompiler();
		String javaCodeStr = getHelloWordCode();
		compiler.cook(new StringReader(javaCodeStr));
		Class<?> clss = compiler.getClassLoader().loadClass("com.test.HelloWorld");
		log.info("result={}", clss.getName());
		Method mainMethod = clss.getDeclaredMethod("main", String[].class);
		mainMethod.invoke(null, (Object) new String[] {});
	}

	@Test
	public void testJaninoParseJavaCode2() throws Exception {
		SimpleCompiler compiler = new SimpleCompiler();
		String javaCodeStr = getHelloWordCode2();
		System.out.println(javaCodeStr);
		compiler.cook(new StringReader(javaCodeStr));
		Class<?> clss = compiler.getClassLoader().loadClass("com.test.MyStringTrim");
		log.info("class name={}", clss.getName());
		Method mainMethod = clss.getDeclaredMethod("trimStr", String.class);
		String result = (String) mainMethod.invoke(clss.newInstance(), "   test  ");
		log.info("result={}", result);
	}

	@Test
	public void testJavaCompilerCode() throws Exception {
		String javaCodeStr = getHelloWordCode();
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject fileObject = new MyJavaFileObjectFromString("HelloWorld", javaCodeStr);
		CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", "./target/test-classes"), null,
				Arrays.asList(fileObject));
		boolean success = task.call();
		if (!success) {
			throw new RuntimeException("编译失败");
		}
		URL[] urls = new URL[] { new URL("file:/" + "./target/test-classes") };
		URLClassLoader classLoader = new URLClassLoader(urls);
		Class<?> clss = classLoader.loadClass("com.test.HelloWorld");
		Method method = clss.getDeclaredMethod("main", String[].class);
		method.invoke(null, (Object) new String[] {});
		classLoader.close();
	}
	
	@Test
	public void testJavaCompilerCode2() throws Exception {
		String javaCodeStr = getHelloWordCode2();
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		JavaFileObject fileObject = new MyJavaFileObjectFromString("MyStringTrim", javaCodeStr);
		CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", "./target/test-classes"), null,
				Arrays.asList(fileObject));
		boolean success = task.call();
		if (!success) {
			throw new RuntimeException("编译失败");
		}
		URL[] urls = new URL[] { new URL("file:/" + "./target/test-classes") };
		URLClassLoader classLoader = new URLClassLoader(urls);
		Class<?> clss = classLoader.loadClass("com.test.MyStringTrim");
		Method mainMethod = clss.getDeclaredMethod("trimStr", String.class);
		String result = (String) mainMethod.invoke(clss.newInstance(), "   test  ");
		log.info("result={}", result);
		classLoader.close();
	}
	

	private String getHelloWordCode2() {
		String str = "package com.test;\n" + "\n" + "import org.apache.commons.lang3.StringUtils;\n"
				+ "import org.slf4j.Logger;\n" + "import org.slf4j.LoggerFactory;\n" + "\n"
				+ "public class MyStringTrim {\n"
				+ "  private static final Logger logger = LoggerFactory.getLogger(MyStringTrim.class);\n" + "\n"
				+ "  public String trimStr(String str){\n" + "    if(str==null){\n" + "      return null;\n" + "    }\n"
				+ "    logger.info(\"trimStr str={}\",str);\n" + "    return StringUtils.trim(str);\n" + "  }\n" + "}";
		return str;
	}

	private String getHelloWordCode() {
		MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class).addParameter(String[].class, "args")
				.addStatement("$T.out.println($S)", System.class, "Hello, World!").build();
		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld").addModifiers(Modifier.PUBLIC).addMethod(main).build();
		JavaFile javaFile = JavaFile.builder("com.test", helloWorld).build();
		return javaFile.toString();
	}
}
