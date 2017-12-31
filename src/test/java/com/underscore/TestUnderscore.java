package com.underscore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.underscore.$;
import com.github.underscore.Function1;
import com.poitl.MyTableDataRenderPolicy;

public class TestUnderscore {
	private static final Logger log = LoggerFactory.getLogger(MyTableDataRenderPolicy.class);

	@Test
	public void testUnderscoreChunk() {
		List<String> strList = Arrays.asList("a", "b", "c", "d", "e");
		List<List<String>> rtn = $.chunk(strList, 2);
		log.info("rtn={}", rtn);
	}

	@Test
	public void testUnderscoreMap() {
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(new Employee("test1", 20000));
		empList.add(new Employee("test2", 10000));
		empList.add(new Employee("test3", 5000));
		$.map(empList,new Function1<Employee, Employee>() {
			public Employee apply(Employee emp) {
				emp.setSalary(emp.getSalary() * 1.2);
				return emp;
			}
		});
		log.info("after operate empList={}",empList);
		
		List<Object> nameList = $.pluck(empList, "getName");
		log.info("after operate nameList={}",nameList);
	}

	//http://www.bkjia.com/cjjc/1003437.html
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
		log.info("after operate empList={}",empList);
	}
}
