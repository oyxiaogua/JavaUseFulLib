package com.basic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Scanner;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJavaExecCmd {
	private static final Logger log = LoggerFactory.getLogger(TestJavaExecCmd.class);

	@Test
	public void testExecCmdWithCommandExec() throws Exception{
		//-c执行完命令后原窗口关闭 -k不关闭
		//Process p = Runtime.getRuntime().exec("cmd.exe /c taskkill /F /PID 10188");
//		Process p = Runtime.getRuntime().exec("cmd.exe /c tasklist");
//		Scanner sc = new Scanner(p.getInputStream());
//		while (sc.hasNextLine()) {
//			log.info(sc.nextLine());
//		}
//		sc.close();
		
		CommandLine commandLine = CommandLine.parse("cmd.exe /c tasklist");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(out);
		Executor executor = new DefaultExecutor();
		executor.setStreamHandler(streamHandler);
		executor.execute(commandLine);
		String rtn = new String(out.toByteArray(),"GBK");
		log.info("result=\n{}",rtn);
	}
	
	@Test
	/**
	 * 在指定文件夹下执行cmd命令
	 * @throws Exception
	 */
	public void testExecCmdAtFixDir() throws Exception {
		String dirPath = "D:/redis";
		String cmdStr = "redis-server.exe redis.windows.conf";
		Process p = Runtime.getRuntime().exec(new String[] { "cmd", "/c", cmdStr }, null, new File(dirPath));
		Scanner sc = new Scanner(p.getInputStream());
		while (sc.hasNextLine()) {
			log.info(sc.nextLine());
		}
		sc.close();
	}
}
