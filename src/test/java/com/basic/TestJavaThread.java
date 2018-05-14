package com.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import cn.hutool.core.thread.NamedThreadFactory;


public class TestJavaThread {
	private static final Logger log = LoggerFactory.getLogger(TestJavaThread.class);

	@Test
	/**
	 * 线程自定义名字
	 */
	public void testCreateThreadWithName() throws Exception{
		Thread prinThread = new Thread(new Runnable() {
	           public void run() {
	        	   log.info("{} print hello",Thread.currentThread().getName());
	           }
	       }, "myPrintThread");
		prinThread.start();
		TimeUnit.SECONDS.sleep(3);
		
		Runnable runnable=new Runnable() {
			public void run() {
				log.info("ThreadPoolExecutor {} print hello",Thread.currentThread().getName());
			}
		};
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(10), new NamedThreadFactory("myPrintThreadPool",false));
		executor.submit(runnable);
		executor.shutdown();
		
		ExecutorService executorSevice = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("myPrintThreadPool2-%d").build());
		executorSevice.submit(runnable);
		executorSevice.shutdown();
	}
	
	@Test(expected=NullPointerException.class)
	public void testExecutorServiceExceptionHandlerWithSubmit() throws Exception {
		ExecutorService executorSevice = Executors.newFixedThreadPool(1);
		@SuppressWarnings("null")
		Future<?> future = executorSevice.submit(() -> {
			Object obj = null;
			log.info("never run here={}", obj.toString());
		});
		//不调用get时候 不报异常
		future.get();
		executorSevice.shutdown();
	}
	
	@SuppressWarnings("null")
	@Test
	public void testExecutorServiceExceptionHandlerWithExecute() throws Exception {
		ExecutorService executorSevice = Executors.newFixedThreadPool(1);
		executorSevice.execute(() -> {
			Object obj = null;
			log.info("never run here={}", obj.toString());
		});
		//日志报错
		executorSevice.shutdown();
	}
	
	@SuppressWarnings("null")
	@Test
	public void testExecutorServiceExceptionHandlerWithExecute2() throws Exception {
		ExecutorService executorSevice = Executors.newFixedThreadPool(1, r -> {
			Thread t = new Thread(r);
			t.setUncaughtExceptionHandler((t1, e) -> log.error(t1 + " throws exception: " + e));
			return t;
		});
		executorSevice.execute(() -> {
			Object obj = null;
			log.info("never run here={}", obj.toString());
		});
		executorSevice.shutdown();
	}
}
