package com.poi.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 检查 excel 文件的行数、列数是否超出限制
 *
 */
public class RowColLimitCheck {

	/**
	 * 表示不检测的长度
	 */
	public static final int UN_LIMITED = -1;

	/**
	 * 检测 HSSF（xls）格式的 excel 文件是否超过最大行数、列数的事件监听类
	 */
	static class ColRowLimitHSSFListener implements HSSFListener {
		private int maxSheetCount;
		private int maxRowCount;
		private int maxColCount;
		private int sheetCount;
		private int rowCount;

		public ColRowLimitHSSFListener(int maxSheetCount, int maxRowCount, int maxColCount) {
			this.maxRowCount = maxRowCount;
			this.maxColCount = maxColCount;
		}

		public void processRecord(Record record) {
			if (record.getSid() == BoundSheetRecord.sid) {
				if ((++sheetCount) > maxSheetCount) {
					throw new RowColLimitBreakException("sheet_exceed");
				}
				rowCount = 0; // 新的sheet，重置计数
			} else if (record.getSid() == RowRecord.sid) {
				if (maxRowCount != UN_LIMITED && (rowCount++) > maxRowCount) {
					throw new RowColLimitBreakException("row_exceed");
				}

				RowRecord recordRow = (RowRecord) record;
				if (maxColCount != UN_LIMITED && recordRow.getLastCol() > maxColCount) {
					throw new RowColLimitBreakException("col_exceed");
				}
			}
		}
	}

	/**
	 * 检测后缀为 xls 的excel 是否满足最大行数、列数的限制。
	 *
	 * @param file
	 *            excel 文件路径
	 * @param maxSheetCount
	 *            允许的最大sheet数
	 * @param maxRowCount
	 *            允许的最大行数
	 * @param maxColCount
	 *            允许的最大列数
	 * @return null: 表示没有问题； "sheet_exceed":sheet数量超过限制； "row_exceed":行数超出限制；
	 *         "col_exceed":列数超出限制
	 * 
	 * @throws IOException
	 */
	public static String checkRowColCount(InputStream fin, String postFix, int maxSheetCount, int maxRowCount,
			int maxColCount) throws Exception {
		if (!postFix.equalsIgnoreCase("xls") && !postFix.equalsIgnoreCase("xlsx")) {
			throw new IllegalArgumentException("not xls or xlsx file");
		}

		if (maxRowCount == UN_LIMITED && maxColCount == UN_LIMITED && maxSheetCount == UN_LIMITED) {
			return null;
		}

		if (postFix.equals("xls")) {
			return checkXls(fin, maxSheetCount, maxRowCount, maxColCount);
		} else {
			return checkXlsx(fin, maxSheetCount, maxRowCount, maxColCount);
		}
	}

	private static String checkXls(InputStream fin, int maxSheetCount, int maxRowCount, int maxColCount)
			throws IOException {
		InputStream din = null;
		POIFSFileSystem poifs = new POIFSFileSystem(fin);
		try {
			din = poifs.createDocumentInputStream("Workbook");
			HSSFRequest req = new HSSFRequest();
			req.addListenerForAllRecords(new ColRowLimitHSSFListener(maxSheetCount, maxRowCount, maxColCount));
			HSSFEventFactory factory = new HSSFEventFactory();
			factory.processEvents(req, din);
			return null;
		} catch (RowColLimitBreakException ex) {
			return ex.getMessage();
		} finally {
			IOUtils.closeQuietly(din);
			poifs.close();
		}
	}

	/**
	 * 标签元素名含义： "sheetData":sheet 标签； "row":行标签； "c":单元格； "v":单元格的值
	 */
	static class SheetHandler extends DefaultHandler {
		private int maxRowCount;
		private int maxColCount;
		private int rowCount;
		private int colCount;

		public SheetHandler(int maxRowCount, int maxColCount) {
			this.maxRowCount = maxRowCount;
			this.maxColCount = maxColCount;
		}

		private boolean isRowElement(String name) {
			return "row".equals(name);
		}

		private boolean isValueElement(String name) {
			return "v".equals(name);
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
		}

		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if (isValueElement(name)) {
				// 值
				colCount++;
				if (maxColCount != UN_LIMITED && colCount > maxColCount) {
					throw new RowColLimitBreakException("col_exceed");
				}
			}
		}

		public void endElement(String uri, String localName, String name) throws SAXException {
			if (isRowElement(name)) {
				// 新的记录行开始
				if (colCount > 0) {
					rowCount++;
				}
				if (maxRowCount != UN_LIMITED && rowCount > maxRowCount) {
					throw new RowColLimitBreakException("row_exceed");
				}
				colCount = 0;
			}
		}

		private void reset() {
			rowCount = 0;
			colCount = 0;
		}
	}

	private static String checkXlsx(InputStream fin, int maxSheetCount, int maxRowCount, int maxColCount)
			throws Exception {
		OPCPackage pkg = OPCPackage.open(fin); // 耗时
		try {
			XSSFReader xssfReader = new XSSFReader(pkg);
			XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			SheetHandler handler = new SheetHandler(maxRowCount, maxColCount);
			parser.setContentHandler(handler);

			Iterator<InputStream> sheetsData = xssfReader.getSheetsData();
			int sheetNum = 0;
			while (sheetsData.hasNext()) { // 检查每个 Sheet
				if ((++sheetNum) > maxSheetCount) {
					return "sheet_exceed";
				}
				InputStream sheetStream = (InputStream) sheetsData.next();
				InputSource sheetSource = new InputSource(sheetStream);
				parser.parse(sheetSource);
				handler.reset();
			}
			return null;
		} catch (RowColLimitBreakException e) {
			return e.getMessage();
		} finally {
			IOUtils.closeQuietly(pkg);
		}
	}
}