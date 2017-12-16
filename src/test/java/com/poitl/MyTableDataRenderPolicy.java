package com.poitl;

import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;

/**
 * 自定义表格内容
 * @author xiaogua
 *
 */
public class MyTableDataRenderPolicy extends DynamicTableRenderPolicy{
	private static final Logger log = LoggerFactory.getLogger(MyTableDataRenderPolicy.class);

	public void render(XWPFTable table, Object data) {
		if(!(data instanceof List)){
			return;
		}
		List<Object> objList=(List<Object>) data;
		String[] strArr=null;
		XWPFTableRow row=null;
		XWPFTableCell cell=null;
		//填充表格数据
		for (int r=0,rLen=objList.size();r<rLen;r++) {
			strArr=((String)objList.get(r)).split(";");
			row=table.insertNewTableRow(table.getRows().size());
			for (int i=0,len= strArr.length;i<len;i++) {
				cell = row.getCell(i);
				if(cell==null){
					cell=row.createCell();
				}
				cell.setText(strArr[i]);
			}
		}
		table.removeRow(2);//删除第3行标识列
		
		//合并单元格
		NiceXWPFDocument nice=new NiceXWPFDocument();
		nice.mergeCellsHorizonal(table, 3, 2, 4);//合并第4行
		nice.mergeCellsVertically(table, 0, 3, 4);
		nice.mergeCellsVertically(table, 1, 3, 5);
		nice.mergeCellsVertically(table, 3, 4, 5);
		nice.mergeCellsVertically(table, 4, 4, 5);
		nice.mergeCellsVertically(table, 5, 3, 5);
		//设置合并单元格样式
		XWPFTableRow row3 = table.getRow(3);
		setCellWidthAlign(row3.getCell(0), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		setCellWidthAlign(row3.getCell(1), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		setCellWidthAlign(row3.getCell(2), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		setCellWidthAlign(row3.getCell(3), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		XWPFTableRow row4 = table.getRow(4);
		setCellWidthAlign(row4.getCell(3), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		setCellWidthAlign(row4.getCell(4), ParagraphAlignment.CENTER, STVerticalJc.CENTER);
		
		try {
			nice.close();
		} catch (Exception e) {
			log.error("MyTableDataRenderPolicy close nicexwpfdocument error",e);
		}
		
	}
	
	/**
	 * 设置单元格对齐
	 * @param cell
	 * @param vAlign
	 */
	private void setCellWidthAlign(XWPFTableCell cell,ParagraphAlignment hAlign,STVerticalJc.Enum vAlign) {
		CTTcPr tcPr = getCellCTTcPr(cell);
		if (vAlign != null) {
			CTVerticalJc vJc = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr
					.addNewVAlign();
			vJc.setVal(vAlign);
		}
		if(hAlign!=null){
			setCellHAlign(cell, hAlign);
		}
	}
	
	/***
	 * 设置单元格水平对齐方式
	 * @param cell
	 * @param hAlign
	 */
	public void setCellHAlign(XWPFTableCell cell, ParagraphAlignment hAlign) {
		List<XWPFParagraph> pList = cell.getParagraphs();
		if (pList == null || pList.size() == 0) {
			XWPFParagraph cellP = cell.addParagraph();
			cellP.setAlignment(hAlign);
		}else{
			for (XWPFParagraph cellP : pList) {
				cellP.setAlignment(hAlign);
			}
		}
	}
	
	private CTTcPr getCellCTTcPr(XWPFTableCell cell) {
		CTTc cttc = cell.getCTTc();
		CTTcPr tcPr = cttc.isSetTcPr() ? cttc.getTcPr() : cttc.addNewTcPr();
		return tcPr;
	}

}
