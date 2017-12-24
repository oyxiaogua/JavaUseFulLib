package com.orika;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.poitl.MyTableDataRenderPolicy;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.PropertyResolver;

public class TestOrika {
	private static final Logger log = LoggerFactory.getLogger(MyTableDataRenderPolicy.class);

	@Test
	public void testOrika() {
		// 生成随机内容
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().seed(123L).objectPoolSize(100)
				.randomizationDepth(3).charset(Charset.forName("UTF-8"))
				.timeRange(LocalTime.of(11, 1), LocalTime.of(11, 30))
				.dateRange(LocalDate.now(), LocalDate.now().plusMonths(2)).stringLengthRange(5, 50)
				.collectionSizeRange(1, 10).scanClasspathForConcreteTypes(true).overrideDefaultInitialization(false)
				.build();
		Person person = enhancedRandom.nextObject(Person.class);
		// 对象复制
		final String NESTED_OPEN = PropertyResolver.ELEMENT_PROPERT_PREFIX;
		final String NESTED_CLOSE = PropertyResolver.ELEMENT_PROPERT_SUFFIX;
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.classMap(Person.class, PersonDto.class).field("nameStrList", "displayNameList")
				.field("name.first", "firstName").field("name.last", "lastName")
				.field("nameList" + NESTED_OPEN + "first" + NESTED_CLOSE,
						"nameArr" + NESTED_OPEN + "[0]" + NESTED_CLOSE)
				.field("nameList" + NESTED_OPEN + "last" + NESTED_CLOSE, "nameArr" + NESTED_OPEN + "[1]" + NESTED_CLOSE)
				.byDefault().register();
		MapperFacade mapper = mapperFactory.getMapperFacade();
		PersonDto personDest = mapper.map(person, PersonDto.class);
		Assert.assertNotNull(personDest);
		Assert.assertEquals(person.getAge(), personDest.getAge());
		Assert.assertEquals(person.getAddress(), personDest.getAddress());
		Assert.assertTrue(Double.compare(person.getMoney(), personDest.getMoney()) == 0);

		Assert.assertArrayEquals(person.getNameStrList().toArray(new String[0]),
				personDest.getDisplayNameList().toArray(new String[0]));
		Assert.assertEquals(person.getBirthDate(), personDest.getBirthDate());

		Assert.assertEquals(person.getName().getFirst(), personDest.getFirstName());
		Assert.assertEquals(person.getName().getLast(), personDest.getLastName());
		Assert.assertEquals(person.getNameList().get(0).getFirst(), personDest.getNameArr()[0][0]);
		Assert.assertEquals(person.getNameList().get(0).getLast(), personDest.getNameArr()[0][1]);
		Assert.assertEquals(person.getNameList().get(1).getFirst(), personDest.getNameArr()[1][0]);
		Assert.assertEquals(person.getNameList().get(1).getLast(), personDest.getNameArr()[1][1]);
	}
	
	@Test
	public void testDataGen() {
		Person person = Person.random();
		log.info("person={}",person);
	}
}
