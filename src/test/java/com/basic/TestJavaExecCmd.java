package com.basic;

import java.io.ByteArrayOutputStream;

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
}
