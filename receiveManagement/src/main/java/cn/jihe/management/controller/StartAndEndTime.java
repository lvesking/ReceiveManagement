package cn.jihe.management.controller;
//开始查询时间和结束时间
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartAndEndTime {

	public String[] getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date  date =new Date();
		//当前年月日
		String dateYmd=sdf.format(date);
		try {
			cal.setTime(sdf.parse(dateYmd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一是第一天  默认是周日
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String beginDate = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, 7);
		String endDate = sdf.format(cal.getTime());
		String[] dateTimes= {beginDate,endDate};
		return dateTimes;
	}
}
