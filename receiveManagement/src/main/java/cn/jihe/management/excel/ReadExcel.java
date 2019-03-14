package cn.jihe.management.excel;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.jihe.management.exception.ReadExcelException;

public class ReadExcel {
	
	private static final long serialVersionUID = 1L;
	private static final int ROWNUM=5; //初始化读取第几行  0开始
	private static final int CELLNUM=1;//初始化列  
	private static final int BERTHNUM=8; //泊位总共多少时间段 0开始
	private static final int INTERVAL=3; //间隔多少   如 供应商1  数量2 状态3   1开始
	public static  Sheet sheet=null;
	public static Sheet getSheet() {
		//读取的excel文件路径
		String excelPath="E:\\management\\excel\\";
		try {
			File file = new File(excelPath);
			String[] path=file.list();
			File excel = new File(excelPath+path[0]);
	          if (excel.isFile() && excel.exists()) {   //判断文件是否存在
	              String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
	              Workbook wb;
	              //根据文件后缀（xls/xlsx）进行判断
	              if ( "xls".equals(split[1])){  //报错找不到指定的文件
	                   //文件流对象
	                  wb = new HSSFWorkbook(new FileInputStream(excel));
	              }else if ("xlsx".equals(split[1])){
	                  wb = new XSSFWorkbook(new FileInputStream(excel));
	                  
	              }else {
	              	throw new ReadExcelException("文件类型错误");
	              }

	              //开始解析
	              Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
	              return sheet;
	          }
		} catch (Exception e) {
			throw new ReadExcelException("找不到指定的文件");
		}
		  
		return null;
	}
	
	
	public  List<Map<String, String>> getExcel(){
        //excel文件路径
        try {
        		List list = new ArrayList<Map<String, String>>();
        		String Quantity=null;
        		String VendorCode=null;
        		ReadExcel read=new ReadExcel();
        		int[] sumBerth=read.getBerth();  //533433333
        		int firstBerth=6+sumBerth[0];
        		int twoBerth=firstBerth+sumBerth[1];
        		int threeBerth=twoBerth+sumBerth[2];
        		int fourBerth=threeBerth+sumBerth[3];
        		int fiveBerth=fourBerth+sumBerth[4];
        		int sixBerth=fiveBerth+sumBerth[5];
        		int serverBerth=sixBerth+sumBerth[6];
        		int eightBerth=serverBerth+sumBerth[7];
        		
        		int rowCellNum[]=ReadExcel.getFirstRow();
                int firstRowIndex=rowCellNum[0];
                int lastRowIndex=rowCellNum[1];
                for(int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) {   //遍历行
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum()+CELLNUM;//列
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                            	String text=cell.toString().split("\\.")[0];
                                if(text!="") {
                                	Map<String, String> map=new HashMap<String, String>();  
                                	if((cIndex+1)%3==0) {//判断是数量
                                		VendorCode=text;
                                	}else if((cIndex+1)%3==1) {
                                		Quantity=text;
                                		map.put("vendorCode", VendorCode);
                                		map.put("quantity", Quantity);
                                		map.put("fvarCDesc", "");
                                		list.add(map);
                                	}
                                	
                                	String timeState=null;
                                	if(rIndex+1>=6&&rIndex+1<firstBerth) {
                                		timeState="0";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=firstBerth&&rIndex+1<twoBerth) {
                                		timeState="1";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=twoBerth&&rIndex+1<threeBerth) {
                                		timeState="2";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=threeBerth&&rIndex+1<fourBerth) {
                                		timeState="3";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=fourBerth&&rIndex+1<fiveBerth) {
                                		timeState="4";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=fiveBerth&&rIndex+1<sixBerth) {
                                		timeState="5";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=sixBerth&&rIndex+1<serverBerth) {
                                		timeState="6";
                                		map.put("state", timeState);
                                	}else if(rIndex+1>=serverBerth&&rIndex+1<eightBerth) {
                                		timeState="7";
                                		map.put("state", timeState);
                                	}else {
                                		timeState="8";
                                		map.put("state", timeState);
                                	}
                                	
                                	
                                	
                                	if(cIndex+1>=INTERVAL&&cIndex+1<INTERVAL*2) {
                                		map.put("week", "0");
                                	}else if(cIndex+1>=INTERVAL*2&&cIndex+1<INTERVAL*3) {
                                		map.put("week", "1");
                                	}else if(cIndex+1>=INTERVAL*3&&cIndex+1<INTERVAL*4) {
                                		map.put("week", "2");
                                	}else if(cIndex+1>=INTERVAL*4&&cIndex+1<INTERVAL*5) {
                                		map.put("week", "3");
                                	}else if(cIndex+1>=INTERVAL*5&&cIndex+1<INTERVAL*6) {
                                		map.put("week", "4");
                                	}else if(cIndex+1>=INTERVAL*6&&cIndex+1<INTERVAL*7) {
                                		map.put("week", "5");
                                	}else if(cIndex+1>=INTERVAL*7&&cIndex+1<INTERVAL*8) {
                                		map.put("week", "6");
                                	}
                                }
                            }
                        }
                    }
                }
                return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
	

	
	 
	 public List<String> getTime() {
		 List<String> timelist=new ArrayList<String>();
		 int rowCellNum[]=ReadExcel.getFirstRow();
         int firstRowIndex=rowCellNum[0];
         int lastRowIndex=rowCellNum[1];
         for(int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) {   //遍历行
             Row row = sheet.getRow(rIndex);
             if (row != null) {
            	 Cell cell = row.getCell(0);
            	 if (cell != null) {
                 	String text=cell.toString().split("\\.")[0];
                 	if(text!=""&&text.indexOf("-")!=-1) {
                 		timelist.add(text);
                 	}
            	 }
             }
         }
         return timelist;
	 }
	 
	 //获取泊位最大数字
	 public  int[] getBerth() {
		 int [] berthInt=new int[8];
		 int index=0;
		 Integer oldberth=0;
		 Integer berth=0;
		 int rowCellNum[]=ReadExcel.getFirstRow();
         int firstRowIndex=rowCellNum[0];
         int lastRowIndex=rowCellNum[1];
         for(int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) {   //遍历行
             Row row = sheet.getRow(rIndex);
             if (row != null) {
            	 Cell cell = row.getCell(1);
            	 if (cell != null) {
                 	String text=cell.toString().split("\\.")[0];
                 	if(text!="") {
                 		if(Integer.parseInt(text)>oldberth) {
                 			oldberth=Integer.parseInt(text);
                 		}else {
                 			if(index>BERTHNUM) {
								index=0;
							}
                 			berth=oldberth;
                 			oldberth=0;
							berthInt[index]=berth;
							index++;
                 		}
                 	}
            	 }
             }
         }
         return berthInt;
	 }
	 
	 
	 public static int[]  getFirstRow() {
		 sheet=ReadExcel.getSheet();
         int firstRowIndex = sheet.getFirstRowNum()+ROWNUM;   //第一行是列名，所以不读
         int lastRowIndex = sheet.getLastRowNum();
         int[] indexNum= {firstRowIndex,lastRowIndex};
         return indexNum;
	 }
	 
}

