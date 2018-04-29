package com.basic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJavaBasicGrammar {
	private static final Logger log = LoggerFactory.getLogger(TestJava8.class);

	@Test
	public void testDateFormat() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
		String dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
		df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
		dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
	}

	/**
	 * @throws Exception
	 */
	/**
	 * @throws Exception
	 */
	@Test
	/**
	 * 测试零宽度字符
	 */
	public void testZeroWidthSpaceCharacters() throws Exception {
		char zeroWidthCharacter = '\u200B';
		String oneCharZeroWidthStr = String.valueOf(zeroWidthCharacter);
		log.info("oneCharZeroWidthStr str={}, length={}", oneCharZeroWidthStr, oneCharZeroWidthStr.length());
		byte[] bytes = oneCharZeroWidthStr.getBytes("UTF-8");
		log.info("bytes length={},", bytes.length);
		
		String str = "a\u200Bb\u200Cc\u200Dd\uFEFFe";
		log.info("str={}, length={}", str, str.length());

		String removeSpaceStr = str.replaceAll("\\s", "");
		log.info("after remove space str={}, length={}", removeSpaceStr, removeSpaceStr.length());

		String removeZeroWidthSpaceStr = str.replaceAll("[\u200B-\u200D\uFEFF]", "");
		log.info("after remove zero width space str={}, length={}", removeZeroWidthSpaceStr,removeZeroWidthSpaceStr.length());
	
		String rtnStr=removeSupplementaryCharacter(str);
		log.info("rtnStr={}, length={}", rtnStr,rtnStr.length());
		
		str="\u200B\u200C\u200D";
		log.info("rtnStr={}, length={}", str,str.length());
		str="\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\u009f\u05b9\u05ba\u0606\u0607\u0608\u0609\u060a\u063b\u063c\u070f\u076e\u076f\u0770\u0771\u0772\u0773\u0774\u0775\u0776\u0777\u0778\u0779\u077a\u077b\u077c\u077d\u077e\u077f\u0a51\u200b\u200c\u200d\u200e\u200f\u2029\u202a\u202b\u202c\u202d";
		log.info("rtnStr={}, length={}", str,str.length());
		//\uFEFF 表示「零宽不换行空格（Zero Width No-Break Space
		//\u2060 用来表示零宽不换行空格
		str="\uFEFF\u2060";
		log.info("rtnStr={}, length={}", str,str.length());
	}
	
	/**
	 * 删除零宽度字符
	 * @param str
	 * @return
	 */
	private String removeSupplementaryCharacter(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = str.length(); i < len; i++) {
			if (i < len - 1) {
				if (Character.isSurrogatePair(str.charAt(i), str.charAt(i + 1))) {
					i += 1;
					continue;
				}
			}
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}
}
