package com.javatest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.file.CommonIoFileListener;
import com.java.file.CommonIoFileMonitor;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.io.watch.watchers.DelayWatcher;

public class TestFileMonitor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testFileMonitorByCommonIo() throws Exception {
		logger.debug("testFileMonitorByCommonIo start");
		CommonIoFileListener fileListener = new CommonIoFileListener();
		String path = "e:/test_tmp";
		CommonIoFileMonitor m = new CommonIoFileMonitor(5000);
		m.monitor(path, fileListener);
		m.start();
		TimeUnit.MINUTES.sleep(2);
	}

	@Test
	public void testFileMonitorByHutool() throws Exception {
		File file = FileUtil.file("e:/test_tmp");
		//不准确
		WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.EVENTS_ALL);
		Watcher printWatcher=new Watcher() {
			public void onCreate(WatchEvent<?> event, Path currentPath) {
				Object obj = event.context();
				logger.debug("创建：{}-> {}", currentPath, obj);
			}

			public void onModify(WatchEvent<?> event, Path currentPath) {
				Object obj = event.context();
				logger.debug("修改：{}-> {}", currentPath, obj);
			}

			public void onDelete(WatchEvent<?> event, Path currentPath) {
				Object obj = event.context();
				logger.debug("删除：{}-> {}", currentPath, obj);
			}

			public void onOverflow(WatchEvent<?> event, Path currentPath) {
				Object obj = event.context();
				logger.debug("Overflow：{}-> {}", currentPath, obj);
			}
		};
		watchMonitor.setWatcher(new DelayWatcher(printWatcher, 500));
		//监听目录的最大深度，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
		watchMonitor.setMaxDepth(Integer.MAX_VALUE);
		watchMonitor.start();
		TimeUnit.MINUTES.sleep(2);
	}
}
