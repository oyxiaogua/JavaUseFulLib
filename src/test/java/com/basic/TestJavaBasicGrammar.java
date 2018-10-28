package com.basic;

import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bean.PersonBean;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.DoubleMath;
import com.vdurmont.emoji.EmojiParser;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;

import cn.hutool.json.JSONUtil;
import io.github.benas.randombeans.api.EnhancedRandom;

public class TestJavaBasicGrammar {
	private static final Logger log = LoggerFactory.getLogger(TestJavaBasicGrammar.class);

	@Test
	public void testDoubleMinValue(){
		BigDecimal bigDecimal=new BigDecimal(Double.toString(Double.MIN_VALUE));
		log.info("rtn={}",bigDecimal.toPlainString());
		
		bigDecimal=new BigDecimal(Double.toString(Double.MIN_NORMAL));
		log.info("rtn={}",bigDecimal.toPlainString());
	}
	
	@Test
	public void testClassLoaderGetResources() throws Exception{
		String name = "java/sql/Array.class";
	    Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(name);
	    URL url=null;
	    while (urls.hasMoreElements()) {
	        url = urls.nextElement();
	        log.info("url={}",url.toString());
	    }
	}
	
	@Test
	public void testThreadLocal() {
		ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
		log.info("value={}",threadLocal.get());
		threadLocal.set(16);
		log.info("value={}",threadLocal.get());
		threadLocal.remove();
		log.info("value={}",threadLocal.get());
	}
	
	@Test
	public void testClassForName() throws Exception {
		Class.forName("com.bean.StaticFieldClass");
		log.info("-----------------------------------------");
		// 没有对类进行初始化，只是把类加载到了虚拟机中
		ClassLoader.getSystemClassLoader().loadClass("com.bean.StaticFieldClass");
	}

	@Test
	/**
	 * 测试正则捕获组
	 */
	public void testRegexCaptureGroup() {
		String regexStr = "(?<year>\\d{4})-(?<md>(?<month>\\d{2})-(?<date>\\d{2}))";
		String str = "2018-06-25";
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			log.info("group(0) value={}", matcher.group(0));
			log.info("group('year') value={}", matcher.group("year"));
			log.info("group('md') value={}", matcher.group("md"));
			log.info("group('month') value={}", matcher.group("month"));
			log.info("group('date') value={}", matcher.group("date"));
			log.info("------------------------------------------");
			log.info("group(0) value={}", matcher.group(0));
			log.info("group(1) value={}", matcher.group(1));
			log.info("group(2) value={}", matcher.group(2));
			log.info("group(3) value={}", matcher.group(3));
			log.info("group(3) value={}", matcher.group(4));
		}
		log.info("------------------------------------------");
		regexStr = "(?:\\d{4})-((\\d{2})-(\\d{2}))";
		pattern = Pattern.compile(regexStr);
		matcher = pattern.matcher(str);
		if (matcher.matches()) {
			log.info("group(0) value={}", matcher.group(0));
			log.info("group(1) value={}", matcher.group(1));
			log.info("group(2) value={}", matcher.group(2));
			log.info("group(3) value={}", matcher.group(3));
		}
	}
	
	@Test
	public void testFilterMapKeyOrValue(){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "test_1");
		map.put(2, "value_2");
		map.put(3, "tomcat");
		map.put(4, "skill");
		Map<Integer, String> rtnMap = Maps.filterKeys(map, r -> r > 3);
		log.info("rtnMap={}", rtnMap);
		
		rtnMap = Maps.filterValues(map, r -> r.startsWith("t"));
		log.info("rtnMap={}", rtnMap);
	}
	
	@Test
	public void testSortMapByValue() {
		Map<String, String> treeMap = new HashMap<String, String>();
		treeMap.put("mad", "2");
		treeMap.put("kitty", "3");
		treeMap.put("cherry", "1");
		treeMap.put("jack", "4");
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(treeMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		for (Map.Entry<String, String> mapping : list) {
			log.info("key={},value={}",mapping.getKey(),mapping.getValue());
		}
	}

	@Test
	public void testDateFormat() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
		String dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
		df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
		dateStr = df.format(new Date());
		log.info("rtn={}", dateStr);
		
		dateStr =FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.info("rtn={}", dateStr);
	}
	
	@Test
	public void testNumberFormat() throws Exception {
		NumberFormat numberFormat = new DecimalFormat();
		log.info("rtn={}", numberFormat.parse("12abc"));
		log.info("rtn={}", parseNumberComplete("12"));
		log.info("rtn={}", parseNumberComplete("12abc"));
		log.info("rtn={}", parseNumberComplete("abc12"));
	}
	
	public Number parseNumberComplete(String str) {
        ParsePosition pp = new ParsePosition(0);//从第一个字符开始解析
        NumberFormat numberFormat = new DecimalFormat();
        Number result = numberFormat.parse(str, pp);
        return pp.getIndex() == str.length() ? result : null;
    }

	@Test
	public void testCodePoint(){
		String testCode = "ab\ud83d\ude03";
        int total = testCode.codePointCount(0, testCode.length());
        int index,codePoint;
        for(int i = 0; i < total; i++) {
            index = testCode.offsetByCodePoints(0, i);
            codePoint = testCode.codePointAt(index);
            log.info("index={},codepoint={}",index,codePoint);
        }
	}
	
	@Test
	public void testUnicode(){
		String str="\u00df";
		log.info("b={}", str);
		str="\u03c2";
		log.info("c={}", str);
		//unicode组合
		str="\u0041";
		log.info("a={}", str);
		str="\u00c1";
		log.info("a={}", str);
		str="\u0041\u0301";
		log.info("a={}", str);
		//字符归一化
		String eUnicode="\u0065\u0308";
		log.info("e={}", eUnicode);
		String eUnicodeN = Normalizer.normalize(eUnicode, Form.NFKC);
		log.info("normalize e={}", eUnicodeN);
		String eUnicode2="\u00eb";
		log.info("e={}", eUnicode2);
		String eUnicodeN2 = Normalizer.normalize(eUnicode2, Form.NFKC);
		log.info("normalize e={}", eUnicodeN2);
		log.info("eUnicode=eUnicode2 rtn={},eUnicodeN=eUnicodeN2 rtn={}",eUnicode.equals(eUnicode2),eUnicodeN.equals(eUnicodeN2));
		
		//e
		str="\u1ec7";
		log.info("e={}", str);
		str="\u1EB9\u0302";
		log.info("e={}", str);
		str="\u00ea\u0323";
		log.info("e={}", str);
		str="\u0065\u0323\u0302";
		log.info("e={}", str);
		
		str="\u26a0";
		log.info("三角={}", str);
		str="\u26a0\ufe0e";
		log.info("三角={}", str);
		str="\u26a0\ufe0f";
		log.info("三角={}", str);
		
		char[] charArr = Character.toChars(0x1F601);
		str = String.valueOf(charArr);
		log.info("from codepoint str={},codePointAt(0)={}", str,str.codePointAt(0));
		String rtnStr = EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE);
		log.info("after parseToAliases str={}", rtnStr);

		str="\u0338";
		log.info("/={}", str);
		str="\u2010";
		log.info("-={}", str);
		str="\u2019";
		log.info("'={}", str);
		str="\u2027";
		log.info(".={}", str);
		str="\u30ad";
		log.info("≠={}", str);
		str="\u200c";//零宽度非连接器
		log.info("black={},length={}", str,str.length());
		str="\u200d";//零宽度连接器
		log.info("black={},length={}", str,str.length());
		str="\u1F61C";
		log.info("wc={},length={},charAt(0)={}", str,str.length(),str.charAt(0));
		log.info("regex replace={}",str.replaceAll(".", "-"));
		
		log.info("str={}","\u206f");
		for(int i=0x2000;i<=0x206f;i++){
			charArr = Character.toChars(i);
			str = String.valueOf(charArr);
			log.info("{}={}___",i,str);
		}
		str="测试\u6D4B\u8BD5unicode";
		log.info("str={}",fromUnicode(str));
		str="\ud83d\udd11";
		rtnStr = EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE);
		log.info("after parseToAliases str={}", rtnStr);
		log.info("UTF8: {}", bytesToBits(str.getBytes(Charset.forName("utf-8"))));
		//前面FEFF大端在左(BOM)表示byte顺序 缺省时默认大端在左
	    log.info("UTF16: {}", bytesToBits(str.getBytes(Charset.forName("utf-16"))));
	    log.info("UTF32: {}", bytesToBits(str.getBytes(Charset.forName("utf-32"))));
	}
	
	@Test
	public void testIterateCodePoint() {
		String str = "a中\ud83d\udd11a中";
		for (int i = 0; i < str.length(); i++) {
			int codePoint = str.codePointAt(i);
			log.info("code point at {}: {},isSupplementaryCodePoint:{}", i, codePoint,Character.isSupplementaryCodePoint(codePoint));
			if (Character.isSupplementaryCodePoint(codePoint)){
				i++;
			}
		}
		log.info(filterUtf8mb4(str));
	}
	
	public String filterUtf8mb4(String str) {
		final int LAST_BMP = 0xFFFF;//辅助平面大于FFFF
		StringBuilder sb = new StringBuilder(str.length());
		for (int i = 0; i < str.length(); i++) {
			int codePoint = str.codePointAt(i);
			if (codePoint < LAST_BMP) {
				sb.appendCodePoint(codePoint);
			} else {
				i++;
			}
		}
		return sb.toString();
	}
	
	public String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    public String bytesToBits(byte[] bytes) {
        String s = "";
        for (byte b : bytes) {
            s += byteToBit(b) + " ";
        }
        return s;
    }
    
	private String fromUnicode(String unicode) {
		// 其中\\\\u\\w{4}为匹配unicode，后面用X代替；((?!X).)*为否定向后（右）环视匹配，匹配不包含X的内容；((?!X).)*+中的加号为占有优先，放弃后续备用路径，提高正则表达式的效率
		Pattern pattern = Pattern.compile("(((?!\\\\u\\w{4}).)*+)(\\\\u(\\w{4}+))?(((?!\\\\u\\w{4}).)*+)");
		Matcher matcher = pattern.matcher(unicode);
		StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			buffer.append(matcher.group(1));
			if (matcher.group(4) != null) {
				char letter = (char) Integer.parseInt(matcher.group(4), 16);
				buffer.append(letter);
			}
			buffer.append(matcher.group(5));
		}
		return buffer.toString();
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
		log.info("after remove zero width space str={}, length={}", removeZeroWidthSpaceStr,
				removeZeroWidthSpaceStr.length());

		String rtnStr = removeSupplementaryCharacter(str);
		log.info("rtnStr={}, length={}", rtnStr, rtnStr.length());

		str = "\u200B\u200C\u200D";
		log.info("rtnStr={}, length={}", str, str.length());
		str = "\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008a\u008b\u008c\u008d\u008e\u008f\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009a\u009b\u009c\u009d\u009e\u009f\u05b9\u05ba\u0606\u0607\u0608\u0609\u060a\u063b\u063c\u070f\u076e\u076f\u0770\u0771\u0772\u0773\u0774\u0775\u0776\u0777\u0778\u0779\u077a\u077b\u077c\u077d\u077e\u077f\u0a51\u200b\u200c\u200d\u200e\u200f\u2029\u202a\u202b\u202c\u202d";
		log.info("rtnStr={}, length={}", str, str.length());
		// \uFEFF 表示「零宽不换行空格（Zero Width No-Break Space
		// \u2060 用来表示零宽不换行空格
		str = "\uFEFF\u2060";
		log.info("rtnStr={}, length={}", str, str.length());
	}

	@Test
	public void testRemoveEmojiFromStr() {
		String str = "test\uD83D\uDC66\uD83C\uDFFFaaa";
		log.info("before process str={}", str);
		String emojiStr = "An 😀awesome 😃string with a few 😉emojis!";
		String rtnStr = EmojiParser.parseToAliases(emojiStr, FitzpatrickAction.REMOVE);
		log.info("after parseToAliases str={}", rtnStr);
		rtnStr = EmojiParser.removeAllEmojis(str);
		log.info("after remove emoji str={}", rtnStr);
	}

	@Test
	public void testHashMapPutAllObjectRef() {
		Map<String, PersonBean> personMap = new HashMap<String, PersonBean>();
		List<String> nameList = Lists.newArrayList("test_1", "test_2", "test_3");
		PersonBean personOne = EnhancedRandom.random(PersonBean.class, "addressList");
		personOne.setAddressList(nameList);
		personMap.put("test_key_1", personOne);
		log.info("before operrate map={}", JSONUtil.toJsonStr(personMap));
		Map<String, PersonBean> anotherPersonMap = new HashMap<String, PersonBean>();
		anotherPersonMap.putAll(personMap);
		personMap.get("test_key_1").getAddressList().add("test_4");
		log.info("after operrate map={}", JSONUtil.toJsonStr(anotherPersonMap));
	}

	@Test
	public void testOjectsEquals() {
		String str = null;
		String str2 = "test";
		Assert.assertFalse(Objects.equal(str, str2));
		Assert.assertTrue(Objects.equal(str, null));
	}

	@Test
	public void testDoubleEqual() {
		double score = 1.23456d;
		double score2 = 1.234559999d;
		double e = 1.0E-8;
		Assert.assertTrue(DoubleMath.fuzzyEquals(score, score2, e));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCollectionsSortFailed() {
		List<Integer> ases = new ArrayList<>(Arrays.asList(-1, 0, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, -1, 0, 1, 1, 1, 0,
				0, 0, 0, -1, 1, 1, 1, 1, -1, -1, -1, -1, 1));
		Collections.sort(ases, new Comparator<Integer>() {
			public int compare(Integer a, Integer b) {
				return a > b ? 1 : -1;
			}
		});
	}

	@Test
	public void testCollectinsIsEmpty() {
		List<String> list = null;
		Assert.assertTrue(CollectionUtils.isEmpty(list));
		list = new ArrayList<String>(32);
		Assert.assertTrue(CollectionUtils.isEmpty(list));
	}

	@Test
	public void testGenericsAndVarargs() {
		List<String> rtnList = pickRandomTwoElement("test_1", "test_2", "test_3");
		log.info("rtn={}", rtnList);
	}

	@Test
	public void testConvertListToArray() {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("1_key_1", "1_value_1");
		map.put("1_key_2", "1_value_2");
		mapList.add(map);
		map = new HashMap<String, String>();
		map.put("2_key_1", "2_value_1");
		map.put("2_key_2", "2_value_2");
		map.put("2_key_3", "2_value_3");
		mapList.add(map);
		String[][] strArr = new String[mapList.size()][];
		for (int i = 0; i < mapList.size(); i++) {
			strArr[i] = mapList.get(i).values().toArray(new String[mapList.get(i).values().size()]);
		}
		log.info("arr={}", Arrays.deepToString(strArr));
	}

	@Test
	public void testListSysCurrentFonts() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
		// classPathResource.getInputStream()));
		for (String fontName : ge.getAvailableFontFamilyNames()) {
			log.info("font name ={}", fontName);
		}
	}

	@Test
	public void testRegexQE() {
		// \Q \E表示二者之间的内容是正则表达式中的常量字符串
		String str = "3\\Q.\\E14";//str="(3\\.14)";
		String inputStr = "测试3.14";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(inputStr);
		Assert.assertTrue(matcher.find());
		log.info("match result={}", matcher.group());
	}

	@SuppressWarnings("hiding")
	private <T> List<T> pickRandomTwoElement(T a, T b, T c) {
		switch (ThreadLocalRandom.current().nextInt(3)) {
		case 0:
			return Lists.newArrayList(a, b);
		case 1:
			return Lists.newArrayList(a, c);
		case 2:
			return Lists.newArrayList(b, c);
		}
		throw new AssertionError();
	}

	/**
	 * 删除零宽度字符
	 * 
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
