package cn.jihe.management.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataTimeService {

	 //根据年月日判断是周几
	 public  Integer dateToWeek(String datetime) {
	        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance(); // 获得一个日历
	        Date datet = null;
	        try {
	            datet = f.parse(datetime);
	            cal.setTime(datet);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        Integer w = cal.get(Calendar.DAY_OF_WEEK); // 指示一个星期中的某天。
	        if (w < 1)
	            w = 0;
	        return w-1;
	    }
	 
	 
	//判断现在时间是否在一个
		 public  boolean isEffectiveDate(String Time, String beginTime, String overTime) {
			 	Date nowTime; 
			 	Date startTime;
			 	Date endTime;
			 	
			 	if(beginTime.indexOf("：")!=-1) {
			 		beginTime=beginTime.replace("：", ":");
			 	}
			 	if(overTime.indexOf("：")!=-1) {
			 		overTime=overTime.replace("：", ":");
			 	}
			 	
				try {
					nowTime= new SimpleDateFormat("HH:mm:ss").parse(Time);
					startTime = new SimpleDateFormat("HH:mm:ss").parse(beginTime);
					endTime = new SimpleDateFormat("HH:mm:ss").parse(overTime);
					if (nowTime.getTime() == startTime.getTime()
							|| nowTime.getTime() == endTime.getTime()) {
						return true;
					}
					
					Calendar date = Calendar.getInstance();
					date.setTime(nowTime);
					
					Calendar begin = Calendar.getInstance();
					begin.setTime(startTime);
					
					Calendar end = Calendar.getInstance();
					end.setTime(endTime);
					
					if (date.after(begin) && date.before(end)) {
						return true;
					} else {
						return false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return false;
		    }
		
		 
}
