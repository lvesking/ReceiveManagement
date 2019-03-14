package cn.jihe.management.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.jihe.management.services.ListService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//重新保存写入excel表
public class WriteExcel {
	private static final int ROWNUM=5; //初始化读取第几行  0开始
	 public static boolean replaceModel(Map item, String sourceFilePath, String targetFilePath) {
	        boolean bool = true;
	        try {
	        	ListService listService=new ListService();
        		JSONArray jsonArray=listService.getUltimatelyData();
	            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(sourceFilePath));
	            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
	            int firstRowIndex = sheet.getFirstRowNum()+ROWNUM;
	            int lastRowIndex = sheet.getLastRowNum();
	            for(int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) {   //遍历行
	            	Row row = sheet.getRow(rIndex);
	                if(row!=null) {
	                    int num = row.getLastCellNum();
	                    for(int i=2;i<num;i++) {//列
	                        XSSFCell cell=  (XSSFCell) row.getCell(i);
	                        if(cell!=null) {
	                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	                        }
	                        if(cell==null || cell.getStringCellValue()==null) {
	                            continue;
	                        }
	                        
	                        String value= cell.getStringCellValue();
	                        if(i%3==2&&!"".equals(value)) {
	                        		for (int j = 0; j < jsonArray.size(); j++) {
										JSONObject jsonObject=jsonArray.getJSONObject(j);
										String v = jsonObject.get("vendorCode").toString();
										String cc = jsonObject.get("colorCode").toString();
										XSSFCell cells=  (XSSFCell) row.getCell(i+2);
		                        		CellStyle style = xssfWorkbook.createCellStyle();
		                        		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND); //设置前景填充样式
	                        			if(v.equals(value)) {
	                        				if("1".equals(cc)) {
	                        					style.setFillForegroundColor(IndexedColors.RED.getIndex());//设置颜色
	                        				}else if("0".equals(cc)){
	                        					style.setFillForegroundColor(IndexedColors.GREEN.getIndex());//设置颜色
	                        				}
	                        				cells.setCellStyle(style);
		                        			jsonArray.remove(j);
		                        			break;
	                        			}
									}
	                        		
	                        }
	                        
	                        
	                        if(!"".equals(value)) {
	                            Set<String> keySet = item.keySet();
	                            Iterator<String> it = keySet.iterator();
	                            while (it.hasNext()) {
	                                String text = it.next();
	                                if(value.equalsIgnoreCase(text)) {
	                                    cell.setCellValue((String)item.get(text));
	                                    break;
	                                }
	                            }
	                        } else {
	                            cell.setCellValue("");
	                        }
	                        
	                    }
	                }
	            }

	            // 输出文件
	            FileOutputStream fileOut = new FileOutputStream(targetFilePath);
	            xssfWorkbook.write(fileOut);
	            fileOut.close();

	        } catch (Exception e) {
	            bool = false;
	            e.printStackTrace();
	        }
	        return bool;
	    }
}
