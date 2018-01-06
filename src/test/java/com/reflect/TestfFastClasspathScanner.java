package com.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orika.Person;
import com.poi.excel.ReadExcelWithXSSFReader;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.matchprocessor.FileMatchProcessor;
import io.github.lukehutch.fastclasspathscanner.scanner.MethodInfo;

public class TestfFastClasspathScanner {
	private static final Logger log = LoggerFactory.getLogger(ReadExcelWithXSSFReader.class);

	@Test
	public void testGetClassAndMethod() {
		String[] pkgArr = new String[] { "com.poitl" };
		List<String> strList = new FastClasspathScanner(pkgArr).strictWhitelist().scan().getNamesOfAllStandardClasses();
		log.info("strList={}", strList);

		List<MethodInfo> methodList = new FastClasspathScanner("com").enableMethodInfo().ignoreMethodVisibility().scan()
				.getClassNameToClassInfo().get(Person.class.getName()).getMethodInfo();
		log.info("methodList={}", methodList);
	}
	
	@Test
	public void testFileMatchProcessor() {
		 Set<String> classSet = new HashSet<String>();
		 FastClasspathScanner scanner = new FastClasspathScanner("io.github.lukehutch.fastclasspathscanner.utils").matchFilenameExtension("class", new FileMatchProcessor() {
			public void processMatch(String relativePath, InputStream inputStream, long lengthBytes)
					throws IOException {
				classSet.add(relativePath);
			}
         });
		 scanner.scan();
		 log.info("classSet={}", classSet);
	}
}
