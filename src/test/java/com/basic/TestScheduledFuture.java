package com.basic;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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

		TimeUnit.MINUTES.sleep(1);
		service.shutdown();
	}
}
