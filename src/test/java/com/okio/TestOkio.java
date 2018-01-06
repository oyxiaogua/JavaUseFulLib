package com.okio;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class TestOkio {
	private static final Logger log = LoggerFactory.getLogger(TestOkio.class);

	@Test
	public void testOkioReadAndWriteString() throws Exception {
		Path path = Files.createTempFile("tmp_", "txt");
		File file = path.toFile();

		try (BufferedSink bufferSink = Okio.buffer(Okio.sink(file))) {
			bufferSink.writeUtf8("测试\n");
			bufferSink.writeString("测试中文", Charset.forName("utf-8"));
		}

		// 读取数据
		try (BufferedSource bufferedSource = Okio.buffer(Okio.source(file))) {
			String str = bufferedSource.readByteString().utf8();
			log.info("-->{}", str);
		}
		try (BufferedSource bufferedSource = Okio.buffer(Okio.source(file))) {
			String str = bufferedSource.readUtf8Line();
			while (str != null) {
				log.info("-->{}", str);
				str = bufferedSource.readUtf8Line();
			}
		}
	}

	@Test
	public void testOkioCopyFile() throws Exception {
		String filePath = "src/test/resources/flower.jpg";
		String newFilePath = "src/test/resources/flower2.jpg";
		BufferedSource bufferedSource = Okio.buffer(Okio.source(new File(filePath)));
		BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(newFilePath)));
		bufferedSink.writeAll(bufferedSource);
		bufferedSink.close();
		bufferedSource.close();
	}
}
