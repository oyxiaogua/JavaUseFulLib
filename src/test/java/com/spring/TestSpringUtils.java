package com.spring;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

public class TestSpringUtils {
	private static final Logger log = LoggerFactory.getLogger(TestSpringUtils.class);

	@Test
	public void testSpringEncodedResource() throws Exception {
		Resource res = new ClassPathResource("/testFile.txt");
		EncodedResource encRes = new EncodedResource(res, "UTF-8");
		String rtnStr = FileCopyUtils.copyToString(encRes.getReader());
		log.info("rtn={}", rtnStr);

		InputStream in = (new DefaultResourceLoader()).getResource("classpath:/testFile.txt").getInputStream();
		rtnStr = IOUtils.toString(in, "UTF-8");
		log.info("rtn={}", rtnStr);
	}

	@Test
	public void testPathMatchingResourcePatternResolver() throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resourceArr = resolver.getResources("classpath*:xml/*.xml");
		for (Resource resource : resourceArr) {
			log.info("rtn={}", resource.getDescription());
		}
	}
}
