package cn.jihe.management.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PresentWeek {
	public Integer getPresentWeek() {
		Date ymd=new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = null;  
		try {  
			//当前年月日
			String dateYmd=format.format(ymd);
		    date = format.parse(dateYmd);  
		} catch (ParseException e) {  
		    e.printStackTrace();  
		}  
		  
		Calendar calendar = Calendar.getInstance();  
		calendar.setFirstDayOfWeek(Calendar.MONDAY-1);  //以星期天开始
		calendar.setTime(date);  
		Integer week= calendar.get(Calendar.WEEK_OF_YEAR); 
		
		return week;
	}

}
