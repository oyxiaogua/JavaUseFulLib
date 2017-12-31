package com.xlsxstreamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monitorjbl.xlsx.StreamingReader;
import com.poi.excel.ReadExcelWithXSSFReader;
import com.poi.excel.RowColLimitCheck;
import com.xiaoleilu.hutool.poi.excel.ExcelUtil;

/**
 * 测试读取Excel
 * @author xiaogua
 *
 */
public class TestXlsxStreamer {
	private static final Logger log = LoggerFactory.getLogger(TestXlsxStreamer.class);
	private static final String filePath = "src/test/resources/testExcel.xlsx";

	@Test
	public void testReadXlsxWithXSSFReader() throws Exception {
		ReadExcelWithXSSFReader t=new ReadExcelWithXSSFReader();
		t.process(filePath);
	}
	
	@Test
	public void testReadXlsxWithHutoolExcelUtil() throws Exception {
		ExcelUtil.read07BySax(new File(filePath), 0, new PrintExcelRowHandler());
	}

	@Test
	public void testReadXlsxWithXlsxStreamer() throws Exception {
		InputStream is = new FileInputStream(new File(filePath));
		Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
		StringBuffer sb = new StringBuffer();
		for (Sheet sheet : workbook) {
			for (Row row : sheet) {
				sb.setLength(0);
				for (int cn = 0, cLen = row.getLastCellNum(); cn < cLen; cn++) {
					Cell cell = row.getCell(cn);
					if (cell != null) {
						sb.append(cell.getStringCellValue());
					}
					if (cn != cLen - 1) {
						sb.append(',');
					}
				}
				log.info("row={}", sb.toString());
			}
		}
		IOUtils.closeQuietly(is);
	}
	
	@Test
	public void testCheckXlsRowNum() throws Exception {
		InputStream is=new FileInputStream(new File(filePath));
		String result=RowColLimitCheck.checkRowColCount(is, "xlsx", 3, 27, 9);
		log.info("result={}",result);
		IOUtils.closeQuietly(is);
	}

}
