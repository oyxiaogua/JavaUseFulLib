package com.reflect;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;

import org.joor.Reflect;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orika.Person;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

public class TestJoor {
	private static final Logger log = LoggerFactory.getLogger(TestJoor.class);

	@Test
	public void testJoor(){
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().seed(123L).objectPoolSize(100)
				.randomizationDepth(3).charset(Charset.forName("UTF-8"))
				.timeRange(LocalTime.of(11, 1), LocalTime.of(11, 30))
				.dateRange(LocalDate.now(), LocalDate.now().plusMonths(2)).stringLengthRange(5, 50)
				.collectionSizeRange(1, 10).scanClasspathForConcreteTypes(true).overrideDefaultInitialization(false)
				.build();
		Person person = enhancedRandom.nextObject(Person.class);
		Reflect.on(person).call("getName").set("first", "测试");
		String firstNameStr = Reflect.on(person).call("getName").call("getFirst").get();
		log.info("rtn={}",firstNameStr);
	}
}
