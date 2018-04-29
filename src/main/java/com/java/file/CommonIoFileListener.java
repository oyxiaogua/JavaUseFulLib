package com.java.file;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonIoFileListener implements FileAlterationListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void onStart(FileAlterationObserver observer) {
		logger.info("onStart.............");
	}

	public void onDirectoryChange(File directory) {
		logger.info("[目录修改]:" + directory.getName());

	}

	public void onDirectoryCreate(File directory) {
		logger.info("[目录新建]:" + directory.getName());

	}

	public void onDirectoryDelete(File directory) {
		logger.info("[目录删除]:" + directory.getName());

	}

	public void onFileChange(File file) {
		logger.info("[文件创建修改]: :" + file.getName());
	}

	public void onFileCreate(File file) {
		logger.info("[文件新建执行]:" + file.getName());

	}

	public void onFileDelete(File file) {
		logger.info("[文件删除]:" + file.getName());
	}

	public void onStop(FileAlterationObserver observer) {
		logger.info("onStop............");
	}

}