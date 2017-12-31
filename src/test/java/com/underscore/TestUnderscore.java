package com.underscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.functors.WhileClosure;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.underscore.$;
import com.github.underscore.Function1;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.poitl.MyTableDataRenderPolicy;

public class TestUnderscore {
	private static final Logger log = LoggerFactory.getLogger(MyTableDataRenderPolicy.class);

	@Test
	public void testUnderscoreChunk() {
		List<String> strList = getTestData();
		List<List<String>> rtn = $.chain(strList).filter(new com.github.underscore.Predicate<String>() {
			public Boolean apply(String value) {
				return value != null;
			}
		}).chunk(2).value();
		log.info("rtn={}", rtn);

		String lastElement=$.last(strList);
		log.info("lastElement={}", lastElement);
		
		List<String> spotList = $.chain(strList).filter(new com.github.underscore.Predicate<String>() {
			public Boolean apply(String value) {
				return value != null && value.startsWith("SPOT");
			}
		}).value();
		log.info("rtn={}", spotList);

		Map<String, List<String>> groupMap = $.groupBy(strList, new Function1<String, String>() {
			public String apply(String value1) {
				if (StringUtils.isBlank(value1)) {
					return null;
				}
				if (value1.length() < 3) {
					return value1;
				}
				return value1.substring(0, 3);
			}
		});
		log.info("groupMap={}", groupMap);
	}

	@Test
	public void testJavaChunk() {
		List<String> strList = getTestData();
		Iterable<String> filterList = Iterables.filter(strList,new com.google.common.base.Predicate<String>(){
			public boolean apply(String input) {
				return input!=null;
			}
		});
		List<List<String>> rtnList = Lists.partition(Lists.newArrayList(filterList),2);
		log.info("rtn={}", rtnList);
		
		String lastElement = Iterables.getLast(strList);
		log.info("lastElement={}", lastElement);
		
		String params="key1=value1&key2=value2&";
		Map<String, String> paramMap = Splitter.on("&").omitEmptyStrings().withKeyValueSeparator("=").split(params);
		log.info("paramMap={}", paramMap);
		
		Function<String, java.util.function.Predicate<String>> startsWithLetter = letter -> name -> name != null
				&& name.startsWith(letter);
		List<String> spotList = strList.stream().filter(startsWithLetter.apply("SPOT"))
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
		log.info("rtn={}", spotList);

		Function<String, String> start3Letter = (name) -> {
			if (StringUtils.isBlank(name)) {
				return "";
			}
			if (name.length() < 3) {
				return name;
			}
			return name.substring(0, 3);
		};
		//不能出现空值key
		Map<String, Collection<String>> groupMap = strList.stream().collect(
				Collectors.groupingBy(start3Letter::apply, HashMap::new, Collectors.toCollection(ArrayList::new)));
		log.info("groupMap={}", groupMap);
	}

	@Test
	public void testUnderscoreMap() {
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(new Employee("test1", 20000));
		empList.add(new Employee("test2", 10000));
		empList.add(new Employee("test3", 5000));
		$.map(empList, new Function1<Employee, Employee>() {
			public Employee apply(Employee emp) {
				emp.setSalary(emp.getSalary() * 1.2);
				return emp;
			}
		});
		log.info("after operate empList={}", empList);

		List<Object> nameList = $.pluck(empList, "getName");
		log.info("after operate nameList={}", nameList);
	}

	@Test
	public void testCollections4Each() {
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(new Employee("test1", 20000));
		empList.add(new Employee("test2", 10000));
		empList.add(new Employee("test3", 5000));
		Closure<Employee> cols = new Closure<Employee>() {
			public void execute(Employee emp) {
				emp.setSalary(emp.getSalary() * 1.2);
			}
		};
		IterableUtils.forEach(empList, cols);
		log.info("after operate empList={}", empList);

		org.apache.commons.collections4.Predicate<Employee> empPre = new org.apache.commons.collections4.Predicate<Employee>() {
			public boolean evaluate(Employee emp) {
				return emp.getSalary() < 10000;
			}
		};
		Closure<Employee> whileCols = WhileClosure.whileClosure(empPre, cols, false);
		IterableUtils.forEach(empList, whileCols);
		log.info("after operate empList={}", empList);
	}

	private List<String> getTestData() {
		return new ArrayList<String>(Arrays.asList("SPOT001", "FWD002", "SWAP003", "SPOT004", "FWD005", null, "SWAP007", "FWD008", "AA",
				"BB"));
	}
}
