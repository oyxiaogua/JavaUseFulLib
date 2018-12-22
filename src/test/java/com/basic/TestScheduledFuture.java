package com.basic;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.date.DateUtil;

public class TestScheduledFuture {
	private static final Logger log = LoggerFactory.getLogger(TestScheduledFuture.class);

	@Test
	public void testScheduledFuture() throws Exception {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		Runnable task1 = new Runnable() {
			public void run() {
				log.info("task 1,time={}", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS"));
			}
		};
		final ScheduledFuture<?> future1 = service.scheduleAtFixedRate(task1, 0, 1, TimeUnit.SECONDS);
		service.schedule(new Callable<String>() {
			public String call() {
				log.info("start to cancel task1");
				future1.cancel(true);
				return "taskcancelled!";
			}
		}, 10, TimeUnit.SECONDS);

		TimeUnit.MINUTES.sleep(10);
		service.shutdown();
	}

	@Test(expected=TimeoutException.class)
	public void testFutureGetBlock() throws Exception {
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.DiscardPolicy());
		Future<?> futureOne = executorService.submit(new Runnable() {
			public void run() {
				log.info("start runable one");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.error("futureOne interrupted", e);
				}
			}
		});

		Future<?> futureTwo = executorService.submit(new Runnable() {
			public void run() {
				log.info("start runable two");
			}
		});

		Future<?> futureThree = null;
		try {
			futureThree = executorService.submit(new Runnable() {
				public void run() {
					log.info("start runable three");
				}
			});
		} catch (Exception e) {
			log.error("futureThree error", e);
		}
		log.info("task on={} ", futureOne.get());
		log.info("task two={}", futureTwo.get());
		//log.info("task three={} ", (futureThree == null ? null : futureThree.get()));
		log.info("task three={} ", (futureThree == null ? null : futureThree.get(3,TimeUnit.SECONDS)));
		executorService.shutdown();
	}
	
	@Test
	public void testFutureGetBlock2() throws Exception {
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.MINUTES,
				new ArrayBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());
		Future<?> futureOne = executorService.submit(new Runnable() {
			public void run() {
				log.info("start runable one");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.error("futureOne interrupted", e);
				}
			}
		});

		Future<?> futureTwo = executorService.submit(new Runnable() {
			public void run() {
				log.info("start runable two");
			}
		});

		Future<?> futureThree = null;
		try {
			futureThree = executorService.submit(new Runnable() {
				public void run() {
					log.info("start runable three");
				}
			});
		} catch (Exception e) {
			log.error("futureThree error", e);
		}
		log.info("task on={} ", futureOne.get());
		log.info("task two={}", futureTwo.get());
		log.info("task three={} ", (futureThree == null ? null : futureThree.get(3,TimeUnit.SECONDS)));
		executorService.shutdown();
	}
	
}
