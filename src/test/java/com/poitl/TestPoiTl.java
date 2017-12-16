package com.poitl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;

/**
 * 测试word文档生成
 * 
 * @author xiaogua
 *
 */
public class TestPoiTl {

	@Test
	public void testPoiTlCreateWord() throws Exception {
		Map<String, Object> dataDefineMap = new HashMap<String, Object>();
		List<Object> datasList = new ArrayList<Object>();
		datasList.add("测试;测试12;测试3;测试4;测试5;测试6");
		datasList.add("测试2;测试22;测试23;测试23;测试23;测试26");
		datasList.add("测试2;测试22;测试32;测试34;测试35;测试26");
		datasList.add("测试3;测试22;测试43;测试34;测试35;测试26");
		
		List<RenderData> headersList = new ArrayList<RenderData>();
		headersList.add(new TextRenderData("1E915D", "省"));
		headersList.add(new TextRenderData("1E915D", "市"));

		List<Object> datasList2 = new ArrayList<Object>();
		datasList2.add("北京;北京");
		datasList2.add("浙江;杭州");
		
		List<TextRenderData> numberTextList = new ArrayList<TextRenderData>();
		numberTextList.add(new TextRenderData("24292E", "列表 1"));
		numberTextList.add(new TextRenderData("24292E", "列表 2"));
		numberTextList.add(new TextRenderData("24292E", "列表 3"));
		NumbericRenderData numberRender = new NumbericRenderData(NumbericRenderData.FMT_DECIMAL,numberTextList);
				
		Style titleStyle=new Style("微软雅黑",24);
		titleStyle.setBold(true);
		titleStyle.setColor("df2d4f");
		TextRenderData titleRender = new TextRenderData("这是标题");
		titleRender.setStyle(titleStyle);
		dataDefineMap.put("title", titleRender);
		dataDefineMap.put("pic", new PictureRenderData(220, 135, "src/test/resources/flower.jpg"));
		dataDefineMap.put("simpletable", new TableRenderData(headersList, datasList2, "", 0));
		dataDefineMap.put("myTableData", datasList);
		dataDefineMap.put("myList", numberRender);
		
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/tableTpl.docx");
		template.registerPolicy("myTableData", new MyTableDataRenderPolicy());
		template.render(dataDefineMap);
		
		FileOutputStream out = new FileOutputStream("e:/test_tmp/525/out_table2.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}
}
