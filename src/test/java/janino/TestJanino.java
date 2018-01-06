package janino;

import java.io.StringReader;
import java.lang.reflect.Method;

import javax.lang.model.element.Modifier;

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

	private String getHelloWordCode() {
		MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class).addParameter(String[].class, "args")
				.addStatement("$T.out.println($S)", System.class, "Hello, World!").build();
		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld").addModifiers(Modifier.PUBLIC).addMethod(main).build();
		JavaFile javaFile = JavaFile.builder("com.test", helloWorld).build();
		return javaFile.toString();
	}
}
