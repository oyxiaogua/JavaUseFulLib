package com.basic;

import java.util.concurrent.Callable;
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

	private static ThreadLocal<String> strThreadLocal = new ThreadLocal<String>() {
		protected String initialValue() {
			return "initValue";
		}
	};
	private static ThreadLocal<StringBuffer> strBufferThreadLocal = new ThreadLocal<StringBuffer>() {
		protected StringBuffer initialValue() {
			return new StringBuffer("initValue");
		}
	};
	private static ThreadLocal<String> strInThreadLocal = new InheritableThreadLocal<String>() {
		protected String initialValue() {
			return "initValue";
		}
	};
	
	private static ThreadLocal<StringBuffer> strBufferInThreadLocal = new InheritableThreadLocal<StringBuffer>() {
		protected StringBuffer initialValue() {
			return new StringBuffer("initValue");
		}
	};
	
	@Test
	public void testThreadLocalTransmitValue() throws Exception{
		strThreadLocal.set("parentStr");
		strBufferThreadLocal.set(new StringBuffer("parentStrBuffer"));
		log.info("before parent thread name={},value={}",Thread.currentThread().getName(),strThreadLocal.get());
		log.info("before parent thread name={},value={}",Thread.currentThread().getName(),strBufferThreadLocal.get());
		for (int i = 0; i < 2; i++) {
			new Thread(){
				public void run() {
					log.info("before child thread name={},value={}",Thread.currentThread().getName(),strThreadLocal.get());
					log.info("before child thread name={},value={}",Thread.currentThread().getName(),strBufferThreadLocal.get());
					strThreadLocal.set(Thread.currentThread().getName()+"childStr");
					strBufferThreadLocal.get().append("|"+Thread.currentThread().getName()+"child|");
					log.info("after child thread name={},value={}",Thread.currentThread().getName(),strThreadLocal.get());
					log.info("after child thread name={},value={}",Thread.currentThread().getName(),strBufferThreadLocal.get());
				}
			}.start();
		}
		TimeUnit.SECONDS.sleep(2);
		log.info("after parent thread name={},value={}",Thread.currentThread().getName(),strThreadLocal.get());
		log.info("after parent thread name={},value={}",Thread.currentThread().getName(),strBufferThreadLocal.get());
	}
	
	@Test
	public void testInheritableThreadLocalTransmitValue() throws Exception{
		strInThreadLocal.set("parentStr");
		strBufferInThreadLocal.set(new StringBuffer("parentStrBuffer"));
		log.info("before parent thread name={},value={}",Thread.currentThread().getName(),strInThreadLocal.get());
		log.info("before parent thread name={},value={}",Thread.currentThread().getName(),strBufferInThreadLocal.get());
		for (int i = 0; i < 2; i++) {
			new Thread(){
				public void run() {
					log.info("before child thread name={},value={}",Thread.currentThread().getName(),strInThreadLocal.get());
					log.info("before child thread name={},value={}",Thread.currentThread().getName(),strBufferInThreadLocal.get());
					strInThreadLocal.set(Thread.currentThread().getName()+"childStr");
					strBufferInThreadLocal.set(new StringBuffer("|"+Thread.currentThread().getName()+"child|"));
					//strBufferInThreadLocal.get().append("|"+Thread.currentThread().getName()+"child|");
					log.info("after child thread name={},value={}",Thread.currentThread().getName(),strInThreadLocal.get());
					log.info("after child thread name={},value={}",Thread.currentThread().getName(),strBufferInThreadLocal.get());
				}
			}.start();
		}
		TimeUnit.SECONDS.sleep(2);
		log.info("after parent thread name={},value={}",Thread.currentThread().getName(),strInThreadLocal.get());
		log.info("after parent thread name={},value={}",Thread.currentThread().getName(),strBufferInThreadLocal.get());
	}
	
	@Test
	public void testThreadDeadLock() throws Exception{
		ExecutorService executorSevice = Executors.newSingleThreadExecutor();
		class OneCallable implements Callable<String>{
	         public String call() throws Exception {
	            Future<String> submit = executorSevice.submit(new TwoCallable());
	            return "success:"+submit.get();
	        }
	    }
		Future<String> submit = executorSevice.submit(new OneCallable());
        log.info("rtn={}",submit.get());
        executorSevice.shutdown();
	}
	
	class TwoCallable implements Callable<String> {
		public String call() throws Exception {
			return "annother success";
		}
	}
	
	@Test
	public void testThreadNPE() throws Exception{
		Thread prinThread = new Thread(new Runnable() {
			public void run() {
				 log.info("{} print hello",Thread.currentThread().getName());
				 throw new NullPointerException();
			}
		});
		prinThread.start();
		TimeUnit.SECONDS.sleep(1);
	}
	
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
