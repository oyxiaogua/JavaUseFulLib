package com.reflect;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orika.Person;
import com.poi.excel.ReadExcelWithXSSFReader;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.MethodInfo;

public class TestfFastClasspathScanner {
	private static final Logger log = LoggerFactory.getLogger(ReadExcelWithXSSFReader.class);

	@Test
	public void testGetClasspathResource() {
		String[] pkgArr = new String[] { "com.poitl" };
		List<String> strList = new FastClasspathScanner(pkgArr).strictWhitelist().scan().getNamesOfAllStandardClasses();
		log.info("strList={}", strList);

		List<MethodInfo> methodList = new FastClasspathScanner("com").enableMethodInfo().ignoreMethodVisibility().scan()
				.getClassNameToClassInfo().get(Person.class.getName()).getMethodInfo();
		log.info("methodList={}", methodList);
	}
}
