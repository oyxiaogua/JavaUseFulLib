package com.xlsxstreamer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.poi.excel.sax.handler.RowHandler;

public class PrintExcelRowHandler implements RowHandler {
	private static final Logger log = LoggerFactory.getLogger(TestXlsxStreamer.class);
	private final StringBuffer sb = new StringBuffer();
	private final static char cellSep = ',';

	public void handle(int sheetIndex, int rowIndex, List<Object> rowList) {
		sb.setLength(0);
		for (int i = 0, len = rowList.size(); i < len; i++) {
			sb.append(rowList.get(i));
			if (i != len - 1) {
				sb.append(cellSep);
			}
		}
		log.info("row{}={}", rowIndex, sb.toString());
	}

}
