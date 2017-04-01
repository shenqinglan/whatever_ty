/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-12
 * Id: DateUtil.java,v 1.0 2017-1-12 上午10:30:53 Administrator
 */
package com.whty.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @ClassName DateUtil
 * @author Administrator
 * @date 2017-1-12 上午10:30:53
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DateUtil {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String[] dayOfWeek = {"SUN","MON","TUES","WED","THUR","FRI","SAT"};
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @return
	 * @Description TODO(获得当前静态的时间字符串,以默认的yyyy-MM-dd hh:mm:ss 格式)
	 */
	public static String getLocalDateString(){
		return dateFormat.format(new Date());
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @return
	 * @Description TODO(格式化一个时间,以默认的yyyy-MM-dd hh:mm:ss 格式)
	 */
	public static String getDate(Date date){
		return dateFormat.format(date);
	}
	
	/**
	 * 
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @param fomat
	 * @return
	 * @Description TODO(将时间是格式化成指定的字符串格式)
	 */
	public static String parseDate(Date date , String fomat){
		if(date == null || fomat == null) return null;
		String source = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
		source = dateFormat.format(date);
		return source;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param source
	 * @param fomat
	 * @return
	 * @throws ParseException
	 * @Description TODO(按照指定的时间字符串格式化一个时间)
	 */
	public static Date parseDate(String source , String fomat) throws ParseException{
		if(StringUtils.isEmpty(source) || fomat == null) return null;
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomat);
		date = dateFormat.parse(source);
		return date;
	}
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param source
	 * @return
	 * @throws ParseException
	 * @Description TODO(将时间字符串格式化成一个时间，以默认的yyyy-MM-dd hh:mm:ss 格式)
	 */
	public static Date getDate(String source) throws ParseException{
		Date date = null;
		date = dateFormat.parse(source);
		return date;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 * @Description TODO(返回输入日期n天前的日期)
	 */
	public static String getBeforeDate(Date date,int days,String format) {   
		if(date == null) return null;
	    SimpleDateFormat df = new SimpleDateFormat(format);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return df.format(calendar.getTime());   
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @param days
	 * @return
	 * @Description TODO(返回输入日期n天前的日期)
	 */
	public static Date getBeforeDate(Date date,int days) {   
		if(date == null) return null;
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) - days);   
	    return calendar.getTime();   
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @param days
	 * @param format
	 * @return
	 * @Description TODO(返回输入日期n天后的日期)
	 */
	public static String getAfterDate(Date date,int days,String format) {   
		if(date == null) return null;
	    SimpleDateFormat df = new SimpleDateFormat(format);   
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return df.format(calendar.getTime());   
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @param days
	 * @return
	 * @Description TODO(返回输入日期n天后的日期)
	 */
	public static Date getAfterDate(Date date,int days) {   
		if(date == null) return null;
	    Calendar calendar = Calendar.getInstance();      
	    calendar.setTime(date);   
	    calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR) + days);   
	    return calendar.getTime();   
	}
	
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param month
	 * @return
	 * @Description TODO(返回指定月的最后一天)
	 */
	public static String getLastDay(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] datetimes = month.split("-");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.YEAR, Integer.parseInt(datetimes[0]));
		lastDate.set(Calendar.MONTH, Integer.parseInt(datetimes[1]));
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		//lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param month
	 * @return
	 * @Description TODO(获取指定月的第一天)
	 */
	public static String getFirstDayOfMonth(String month) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] datetimes = month.split("-");
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.YEAR,Integer.parseInt(datetimes[0]));
		firstDate.set(Calendar.MONTH,Integer.parseInt(datetimes[1])-1);
		firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(firstDate.getTime());
		return str;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param d
	 * @return
	 * @Description TODO(获取指定月的天数)
	 */
	public static int getMonthDays(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String date=sdf.format(d);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) -1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param d
	 * @return
	 * @Description TODO(获取指定年的月)
	 */
	public static Integer getMonth(Date d){
	    Calendar lastDate = Calendar.getInstance();
		   lastDate.setTime(d);
		   lastDate.add(Calendar.MONTH, 1);
		   int month = lastDate.get(Calendar.MONTH);
		return month;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param date
	 * @return
	 * @Description TODO(判断日期字符串是否超过了今天之后)
	 */
	public static boolean checkDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String td = sdf.format(today);
		Date d1,d2;
		try {
			d1 = sdf.parse(date);
			d2 = sdf.parse(td);
			if(d1.getTime()>d2.getTime()){
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description TODO(获取指定日期的上周一)
	 */
	public static String getMondayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//如果是星期天,取14天前
		if("SUN".equals(DateUtil.getDayOfWeek(dateTime))){
			cal.add(Calendar.DATE, -14);
		}else{
			cal.add(Calendar.WEEK_OF_YEAR, -1);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sdf.format(cal.getTime());
	}
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description TODO(获取指定日期的上周日)
	 */
	public static String getSundayOfLastWeek(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//不是周日时往后推7天
		if(!"SUN".equals(DateUtil.getDayOfWeek(dateTime))){
			return DateUtil.getAfterDate(cal.getTime(), 7, "yyyy-MM-dd");
		}else{
			return sdf.format(cal.getTime());
		}
	}
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description TODO(判断指定日期为星期几)
	 */
	public static String getDayOfWeek(String dateTime) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		  Calendar c = Calendar.getInstance();
		  c.setTime(format.parse(dateTime));
		  int n = c.get(Calendar.DAY_OF_WEEK) - 1;
		  return DateUtil.dayOfWeek[n];
	}
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @param dateTime
	 * @return
	 * @throws ParseException
	 * @Description TODO(获取指定日期上个月)
	 */
	public static String getLastMonth(String dateTime) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		String lastMonth = sdf.format(cal.getTime());
		return lastMonth.substring(0, 7);
	}
	
	
	/**
	 * @author Administrator
	 * @date 2017-1-12
	 * @return
	 * @throws ParseException
	 * @Description TODO(获取第二天凌晨零点零分零秒)
	 */
	public static Date getTimeOf12AmOfNextDay() throws ParseException{
		Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return  cal.getTime();
	}
	
	/**
	 * 
	 * @author qgc
	 * @date 2015年5月16日
	 * @param date
	 * @param n
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static Date firstDayOfNWeek(Date date,int n){
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
	    int w = cal.get(Calendar.DAY_OF_WEEK);
		return DateUtils.addDays(date, 7*n-w+1);
	}
	public static Date lastDayOfNWeek(Date date,int n){
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
	    int w = cal.get(Calendar.DAY_OF_WEEK);
		return DateUtils.addDays(date, 7*n-w+6);
	}
	/**
	 * 
	 * @author qgc
	 * @date 2015年5月16日
	 * @param date
	 * @param n
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static Date firstDayOfNMonth(Date date,int n){
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
	    int w = cal.get(Calendar.DAY_OF_MONTH);
		Date d= DateUtils.addDays(date,-w+1);
		return DateUtils.addMonths(d, n);
	}
	/**
	 * 
	 * @author qgc
	 * @date 2015年5月16日
	 * @param date
	 * @param n
	 * @return
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static Date firstDayOfNYear(Date date,int n){
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
	    int w = cal.get(Calendar.DAY_OF_YEAR);
		Date d= DateUtils.addDays(date,-w+1);
		return DateUtils.addMonths(d, n);
	}
}
