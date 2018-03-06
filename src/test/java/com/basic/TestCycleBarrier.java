package com.basic;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCycleBarrier {
	private static final Logger log = LoggerFactory.getLogger(TestCycleBarrier.class);

	public static void main(String[] args) {
		CyclicBarrier cyc = new CyclicBarrier(5, new Runnable() {
			public void run() {
				log.info("所有线程执行完毕");
			}
		});
		for (int i = 1; i <= 5; i++) {
			new HelloThread(cyc).start();
		}
		log.info("主线程继续执行");
	}

	static class HelloThread extends Thread {
		private CyclicBarrier cyclicBarrier;

		public HelloThread(CyclicBarrier cyclicBarrier) {
			this.cyclicBarrier = cyclicBarrier;
		}

		public void run() {
			log.info("线程{}正在写入数据...", Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
				if(new Random().nextBoolean()){
					throw new RuntimeException();
				}
				log.info("线程{}写入数据完毕", Thread.currentThread().getName());
			} catch (Exception e) {
				log.error("线程{}写数据异常", Thread.currentThread().getName());
			} finally {
				try {
					cyclicBarrier.await();
				} catch (Exception e) {
					log.error("helloThread error",e);
				}
			}
		}
	}
}