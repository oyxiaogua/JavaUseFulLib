package com.xlsxstreamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

	@Test
	public void testWriteExcel() throws Exception {
		SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
		SXSSFCell sxssfCell = null;
		SXSSFRow sxssfRow = null;
		SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet1");
		sxssfRow = sheet.createRow(0);
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Map<String, String> dataMap = null;
		Iterator<Map.Entry<String, String>> iterator = null;
		Map.Entry<String, String> entry = null;
		for (int i = 1, len = dataList.size(); i <= len; i++) {
			sxssfRow = sheet.createRow(i);
			dataMap = dataList.get(i - 1);
			if(dataMap==null){
				continue;
			}
			iterator = dataMap.entrySet().iterator();
			int cell = 0;
			while (iterator.hasNext()) {
				entry = iterator.next();
				sxssfCell = sxssfRow.createCell(cell);
				sxssfCell.setCellValue(entry.getValue());
				cell++;
			}
			dataMap.clear();
		}
		dataList.clear();
		dataList = null;

		FileOutputStream fos = new FileOutputStream("sys_"+System.currentTimeMillis()+".xlsx");
		sxssfWorkbook.write(fos);
		fos.close();
		sxssfWorkbook.close();
	}
}
