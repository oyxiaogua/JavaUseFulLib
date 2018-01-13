package com.javapoet;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import org.junit.Test;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

public class TestJavaPoet {

	@Test
	public void testCreateJavaCode() {
		List<FieldSpec> fieldList = new ArrayList<FieldSpec>();
		fieldList.add(myStaticFieldWithInitValue());
		fieldList.add(myFieldWithInit());
		fieldList.add(myListField());

		List<MethodSpec> methodList = new ArrayList<MethodSpec>();
		methodList.add(myMethodSpec(myMethodCode()));
		methodList.add(myVargsParamMethod());
		methodList.add(myMethod());
		methodList.add(myMethodCall(myMethod()));

		TypeSpec clazz = myClazz(fieldList, methodList);
		JavaFile file = JavaFile.builder("com.test.howtojavapoet", clazz).build();
		System.out.println(file.toString());
	}

	private TypeSpec myClazz(List<FieldSpec> fieldSpecList, List<MethodSpec> methodSpecList) {
		Builder typeBuild = TypeSpec.classBuilder("Clazz").addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.addTypeVariable(TypeVariableName.get("T")).superclass(String.class)
				.addSuperinterface(Serializable.class)
				.addSuperinterface(ParameterizedTypeName.get(Comparable.class, String.class))
				.addSuperinterface(ParameterizedTypeName.get(ClassName.get(Comparable.class),
						WildcardTypeName.subtypeOf(String.class)))
				.addType(TypeSpec.classBuilder("InnerClass").build()).addStaticBlock(CodeBlock.builder().build())
				.addInitializerBlock(CodeBlock.builder().build());
		for (FieldSpec fieldSpec2 : fieldSpecList) {
			typeBuild.addField(fieldSpec2);
		}
		for (MethodSpec methodSpec2 : methodSpecList) {
			typeBuild.addMethod(methodSpec2);
		}
		return typeBuild.build();
	}

	private FieldSpec myStaticFieldWithInitValue() {
		return FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						TypeVariableName.get("T")), "mFoo", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
				.initializer("new $T()", HashMap.class).build();
	}

	private FieldSpec myFieldWithInit() {
		FieldSpec fieldSpec = FieldSpec.builder(String.class, "testStr", Modifier.PRIVATE).initializer("$S", "test")
				.build();
		return fieldSpec;
	}

	private FieldSpec myListField() {
		ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(List.class, String.class);
		FieldSpec fieldSpec = FieldSpec.builder(parameterizedTypeName, "myList", Modifier.PRIVATE).build();
		return fieldSpec;
	}

	private MethodSpec myMethodSpec(CodeBlock codeBlock) {
		AnnotationSpec suppressWarnAnno = AnnotationSpec.builder(SuppressWarnings.class)
				.addMember("value", "$S", "unchecked").build();
		return MethodSpec.methodBuilder("myMethod").addJavadoc("testSuppressWarning\n").addJavadoc("@see testurl\n")
				.addAnnotation(suppressWarnAnno).addAnnotation(Override.class)
				.addTypeVariable(TypeVariableName.get("T")).addModifiers(Modifier.PUBLIC).returns(int.class)
				.addParameter(String.class, "string").addParameter(TypeVariableName.get("T"), "t")
				.addParameter(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Integer.class),
						WildcardTypeName.subtypeOf(TypeVariableName.get("T"))), "map")
				.addException(IOException.class).addException(RuntimeException.class).addCode(codeBlock).build();
	}

	private MethodSpec myVargsParamMethod() {
		ParameterSpec parameterSpec = ParameterSpec.builder(String[].class, "params").build();
		MethodSpec methodSpec = MethodSpec.methodBuilder("myMethodWithVarg").addModifiers(Modifier.PRIVATE)
				.addParameter(parameterSpec).varargs(true).addCode("$T date = new $T();\n", Date.class, Date.class)
				.addCode("System.out.println(\"test\");\n").build();
		return methodSpec;

	}

	private MethodSpec myMethodCall(MethodSpec methodSpec1) {
		ParameterSpec parameterSpec = ParameterSpec.builder(String.class, "param").build();
		MethodSpec methodSpec2 = MethodSpec.methodBuilder("myMethod2").addModifiers(Modifier.PRIVATE)
				.addParameter(parameterSpec).addCode("System.out.println($N() + $N);\n", methodSpec1, parameterSpec)
				.build();
		return methodSpec2;
	}

	private MethodSpec myMethod() {
		MethodSpec methodSpec1 = MethodSpec.methodBuilder("myMethod1").addModifiers(Modifier.PRIVATE)
				.returns(String.class).addCode("return $S;\n", "test").build();
		return methodSpec1;
	}

	private CodeBlock myMethodCode() {
		return CodeBlock.builder().addStatement("int foo = 1").addStatement("$T bar = $S", String.class, "a string")
				// Object obj = new HashMap<Integer, ? extends T>(5);
				.addStatement("$T obj = new $T(5)", Object.class,
						ParameterizedTypeName.get(ClassName.get(HashMap.class), ClassName.get(Integer.class),
								WildcardTypeName.subtypeOf(TypeVariableName.get("T"))))

				// method(new Runnable(String param) {
				// @Override
				// void run() {
				// }
				// });
				.addStatement("baz($L)",
						TypeSpec.anonymousClassBuilder("$T param", String.class).superclass(Runnable.class)
								.addMethod(MethodSpec.methodBuilder("run").addAnnotation(Override.class)
										.returns(TypeName.VOID).build())
								.build())

				// for
				.beginControlFlow("for (int i = 0; i < 5; i++)").endControlFlow()

				// while
				.beginControlFlow("while (false)").endControlFlow()

				// do... while
				.beginControlFlow("do").endControlFlow("while (false)")

				// if... else if... else...
				.beginControlFlow("if (false)").nextControlFlow("else if (false)").nextControlFlow("else")
				.endControlFlow()

				// try... catch... finally
				.beginControlFlow("try").nextControlFlow("catch ($T e)", Exception.class)
				.addStatement("e.printStackTrace()").nextControlFlow("finally").endControlFlow()

				.addStatement("return 0").build();
	}

}
