package sing.earthquake.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.text.TextUtils;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
	/** 12 */
	public static final String FORMAT_MM = "MM";
	/** 2015 */
	public static final String FORMAT_YYYY = "yyyy";
	/** 15:03:34 */
	public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
	/** 15:03 */
	public static final String FORMAT_HH_MM = "HH:mm";
	/** 12月12日 */
	public static final String FORMAT_MM_DD = "MM月dd日";
	/** 12-12 */
	public static final String FORMAT_MM__DD = "MM-dd";
	/** 2012-02 */
	public static final String FORMAT_MM__DD2 = "yyyy-MM";
	/** 12/12 */
	public static final String FORMAT_MM___DD = "MM/dd";
	/** 20120219 */
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	/** 2012-02-19 */
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	/** 2012-02-19 */
	public static final String FORMAT_YYYY_MM_DD_ZH = "yyyy年MM月dd日";
	/** 2012/02/19 */
	public static final String FORMAT_YYYY_MM_DD_ZH_SLASH = "yyyy/MM/dd";
	/** 0219  2月19日*/
	public static final String FORMAT_MM_DD1 = "MMdd";
	/** 2012-02-19 */
	public static final String FORMAT_YYYY_MM = "yyyy年MM月";
	/** 2012-02-19 05:11 */
	public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_YYYY_MM_DD_HH_MM1 = "MM-dd HH:mm";
	/** 20120219150334 */
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/** 20120219150334 */
	public static final String FORMAT_DATABASE = "yyyyMMddHHmmss";
	/** 20120219150334 */
	public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	/** 2012-02-19 15:03:34 */
	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/** 2012-02-19 15:03:34.876 */
	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	/** 2012-02-19 12时19分 */
	public static final String FORMAT_YYYY_MM_DD_HHMM_WORD = "yyyy-MM-dd HH时mm分";
	/** 2012年02月19日12:19 */
	public static final String FORMAT_YYYY_MM_DD_HHMM_WORD_ZH = "yyyy年MM月dd日HH:mm";
	/** yyyy年MM月dd日HH时mm分ss秒 */
	public static final String FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH = "yyyy年MM月dd日HH时mm分ss秒";
	/** yyyy年MM月dd日 HH时mm分ss秒 */
	public static final String FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH2 = "yyyy年MM月dd日 HH时mm分ss秒";
	 // xxxx/xx/xx
	/**
	 * @author: zhaguitao
	 * @Title: formatString2Date
	 * @Description: 将日期字符串转换成Date对象
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期字符串样式
	 * @return Date对象
	 * @date: 2013-5-22 下午2:07:29
	 */
	public static Date formatString2Date(String dateStr, String format) {
		if (TextUtils.isEmpty(dateStr)) {
			return null;
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @author: zhaguitao
	 * @Title: formatDate2String
	 * @Description: 将日期对象转换成日期字符串
	 * @param date
	 *            日期对象
	 * @param format
	 *            日期字符串样式
	 * @return 日期字符串
	 * @date: 2013-5-22 下午2:12:17
	 */
	public static String formatDate2String(Date date, String format) {
		if (date == null) {
			return "";
		}

		try {
			SimpleDateFormat formatPattern = new SimpleDateFormat(format);
			return formatPattern.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Description:转换数据库时间为UI显示样式
	 * 
	 * @param dbTime
	 *            数据库查询出的时间<br>
	 *            数据库内的时间格式统一为yyyyMMddHHmmss
	 * @param formatType
	 *            目标格式
	 * @return 目标格式的字符串
	 */
	public static String convertDBOperateTime2SpecifyFormat(String dbTime,
			String formatType) {
		// 转换数据库时间为UI显示样式
		return getNewFormatDateString(dbTime, FORMAT_DATABASE, formatType);
	}

	/**
	 * @author: zhaguitao
	 * @Title: formatDateString
	 * @Description: 将日期字符串格式化成数据库样式
	 * @param dateStr
	 *            日期字符串
	 * @param fromFormat
	 *            原始样式
	 * @return 数据库样式的日期字符串
	 * @date: 2013-5-22 上午10:15:35
	 */
	public static String getDBOperateTime(String dateStr, String fromFormat) {
		// 1、将原始日期字符串转换成Date对象
		Date date = formatString2Date(dateStr, fromFormat);

		// 2、将Date对象转换成数据库样式样式字符串
		return formatDate2String(date, FORMAT_DATABASE);
	}

	/**
	 * Description:所有数据库操作时间取值都采用本方法
	 * 
	 * @return 当前时间yyyyMMddHHmmss格式，如：20120219111945
	 */
	public static String getDBOperateTime() {
		return getCurrentTimeSpecifyFormat(FORMAT_DATABASE);
	}

	/**
	 * Description:获取当前日期指定样式字符串
	 * 
	 * @param formatType
	 *            指定的日期样式
	 * @return 当前日期指定样式字符串
	 */
	public static String getCurrentTimeSpecifyFormat(String formatType) {
		Date date = new Date();

		return formatDate2String(date, formatType);
	}

	/**
	 * Description:获取昨天指定样式字符串
	 * 
	 * @param formatType
	 *            指定的日期样式
	 * @return 昨天日期指定样式字符串
	 */
	public static String getYesterdaySpecifyFormat(String formatType) {
		Date date = new Date();
		date = new Date(date.getTime() - 1000 * 60 * 60 * 24);
		return formatDate2String(date, formatType);
	}

	/**
	 * @author: zhaguitao
	 * @Title: getNewFormatDateString
	 * @Description: 格式化日期字符串
	 * @param dateStr
	 *            日期字符串
	 * @param fromFormat
	 *            原始样式
	 * @param toFormat
	 *            目标样式
	 * @return 目标样式的日期字符串
	 * @date: 2013-5-22 上午10:15:35
	 */
	public static String getNewFormatDateString(String dateStr,String fromFormat, String toFormat) {

		// 1、将原始日期字符串转换成Date对象
		Date date = formatString2Date(dateStr, fromFormat);

		// 2、将Date对象转换成目标样式字符串
		return formatDate2String(date, toFormat);
	}

	/**
	 * @author: zhaguitao
	 * @Title: realDateIntervalDay
	 * @Description: 计算日期之间的间隔天数
	 * @param dateFrom
	 *            起始日期
	 * @param dateTo
	 *            结束日期
	 * @return 日期之间的间隔天数
	 * @date: 2013-5-22 下午2:25:28
	 */
	public static int realDateIntervalDay(Date dateFrom, Date dateTo) {
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(dateFrom);

		Calendar toCal = Calendar.getInstance();
		toCal.setTime(dateTo);

		if (fromCal.after(toCal)) {
			// swap dates so that fromCal is start and toCal is end
			Calendar swap = fromCal;
			fromCal = toCal;
			toCal = swap;
		}
		int days = toCal.get(Calendar.DAY_OF_YEAR)
				- fromCal.get(Calendar.DAY_OF_YEAR);
		int y2 = toCal.get(Calendar.YEAR);
		if (fromCal.get(Calendar.YEAR) != y2) {
			fromCal = (Calendar) fromCal.clone();
			do {
				// 得到当年的实际天数
				days += fromCal.getActualMaximum(Calendar.DAY_OF_YEAR);
				fromCal.add(Calendar.YEAR, 1);
			} while (fromCal.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * @author: zhaguitao
	 * @Title: getDateTimeByFormatAndMs
	 * @Description: 将毫秒型日期转换成指定格式日期字符串
	 * @param longTime
	 * @param format
	 * @return
	 * @date: 2013-2-25 下午12:00:51
	 */
	public static String getDateTimeByFormatAndMs(long longTime, String format) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(longTime);

		return formatDate2String(c.getTime(), format);
	}


	public static long currentTime(){
		return formatString2Date(getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD), FORMAT_YYYY_MM_DD).getTime();
	}
	/** 
	 * @Title:getDateBefore
	 * @Description:计算date之前n天的日期 
	 * FORMAT_YYYY_MM_DD
	 */  
	public static String getDateBefore(Date date, int n) {  
		Calendar now = Calendar.getInstance();    
		now.setTime(date);    
		now.set(Calendar.DATE, now.get(Calendar.DATE) - n);    
		return getDateTimeByFormatAndMs(now.getTimeInMillis(),DateUtil.FORMAT_YYYY_MM_DD);  
	}  
	/** 
	 * @Title:getMoretime
	 * @Description:计算是否在两个时间之内
	 * FORMAT  ture or false;
	 */  

	public static boolean getMoretime(String current,String start,String end){
		Date currentTime,startTime,endTime;
		currentTime=formatString2Date(current, FORMAT_YYYY_MM_DD_HH_MM_SS);
		startTime=formatString2Date(start, FORMAT_YYYY_MM_DD_HH_MM_SS);
		endTime=formatString2Date(end, FORMAT_YYYY_MM_DD_HH_MM_SS);

		if(currentTime==null||startTime==null||endTime==null){
			return false;
		}else{
			if(currentTime.getTime()>startTime.getTime()&&currentTime.getTime()<endTime.getTime()){
				return true;
			}else{
				return false;
			}
		}

	}
	
	/**
	 * @Description: 学术圈、论文详情时间展示格式
	 * 					今天 HH:mm 
	 * 					昨天 昨天
	 * 					今年的其他日子  MM/dd 
	 * 					不是今年的 yyyy/MM/dd
	 * @param time 源时间字符串
	 * @return
	 * @return: String
	 */
	public static String getTimeForComment (String time) {
		StringBuffer sBuffer = new StringBuffer();
		if (!TextUtils.isEmpty(time)) {
			if ("今天".equals(isTodayOrYestorday(time))) {
				sBuffer.append(DateUtil.getNewFormatDateString(time, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, DateUtil.FORMAT_HH_MM));
			} else if("昨天".equals(isTodayOrYestorday(time))){
				sBuffer.append("昨天");
			}else if (DateUtil.getNewFormatDateString(time, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, DateUtil.FORMAT_YYYY_MM_DD_HH_MM).substring(0, 4).contains(getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD_HH_MM).substring(0, 4))) {
				//今年的其他日子
				sBuffer.append(DateUtil.getNewFormatDateString(time, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, DateUtil.FORMAT_MM___DD));
			}else {
				// 跨年
				sBuffer.append(DateUtil.getNewFormatDateString(time, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, DateUtil.FORMAT_YYYY_MM_DD_ZH_SLASH));
			}
		}
		return sBuffer.toString();
	}
	
	 /** 
     * 根据日期获得星期 
     * @return
     */
	public static String getWeekOfDate(String dateStr) {
		Date date = formatString2Date(dateStr, FORMAT_YYYY_MM_DD_ZH_SLASH);
		String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		// String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 获取日期是星期的某一天
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	} 
	
	/** 
	  * 判断两个日期是否在同一周 
	  * @return
	  */ 
	public static boolean isSameWeekDates(String dateStr1, String dateStr2) {
		Date date1 = formatString2Date(dateStr1, FORMAT_YYYY_MM_DD_ZH_SLASH);
		Date date2 = formatString2Date(dateStr2, FORMAT_YYYY_MM_DD_ZH_SLASH);
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}

		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Description: 获取当前如期所在星期的周日的日期
	 * @param dateStr  日期字符串
	 * @return: String  格式  ： 2012/02/19 
	 */
	public static String getSunday(String dateStr) {
		Date date = formatString2Date(dateStr, FORMAT_YYYY_MM_DD_ZH_SLASH);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return formatDate2String(calendar.getTime(), FORMAT_YYYY_MM_DD_ZH_SLASH);
	}

	
	/**
	 * @Description: 环信会话时间
	 *   今天 显示：  时 :分          昨天显示：   昨天		
	 *   同一周但在昨天前 显示：周几，      当年显示：xx/xx      跨年显示： xxxx/xx/xx
	 * @param ms 格式为：毫秒值
	 * @return: String
	 */
	public static String getTimeForConversation(Long ms) {
		String time = getDateTimeByFormatAndMs(ms, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH);
		// 根据日期情况显示的指定格式的日期
		String newTime;
		//截取当前年的时间格式2012  和当前 日期的时间格式
		String currentDay = getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD_ZH_SLASH);
		String currentYear = currentDay.substring(0, 4);
		//昨天日期格式2012/02/19
		String yesDate= getYesterdaySpecifyFormat(DateUtil.FORMAT_YYYY_MM_DD_ZH_SLASH);
		
		//先转换格式：2012/02/19
		String timeForma = getNewFormatDateString(time, FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, FORMAT_YYYY_MM_DD_ZH_SLASH);
		//今年
		if (timeForma.contains(currentYear)) {
			if (timeForma.contains(currentDay)) {//当天  05:11 
				newTime = getNewFormatDateString(time,FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, FORMAT_HH_MM);
			}else if (timeForma.contains(yesDate)) {//昨天
				newTime = "昨天";
			}else {// 当年 02/19
				if (isSameWeekDates(currentDay, timeForma)){//同一周    显示周几
					if (getSunday(timeForma).contains(timeForma)) {//本周的周日
						newTime = timeForma.substring(5);
					}else {
						newTime = getWeekOfDate(timeForma);
					}
				}else {
					newTime = timeForma.substring(5);
				}
			}
		}else {//跨年全部显示
			newTime = timeForma;
		}
		return newTime;
	}
	
	
	/**
	 * @Description: 今天和昨天返回“今天”、“昨天”
	 * 				否则返回 “0111月”  11月01日，不考虑跨年
	 * 				否则返回 “011月”  1月01日，不考虑跨年
	 * @param time 格式为：yyyy年MM月dd日HH时mm分ss秒
	 * @return: String
	 */
	public static String getTimeForLine(String time) {
		// 根据日期情况显示的指定格式的日期
		String newTime;
		//截取当前年的时间格式2012  和当前 日期的时间格式
		String currentDay = getCurrentTimeSpecifyFormat(FORMAT_YYYYMMDD);
		//昨天日期格式2012/02/19
		String yesDate= getYesterdaySpecifyFormat(DateUtil.FORMAT_YYYYMMDD);
		//先转换格式：1902  2月19
		String timeForma = getNewFormatDateString(time, FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, FORMAT_MM_DD1);
		if (currentDay.contains(timeForma)) {
			newTime = "今天";
		} else if (yesDate.contains(timeForma)) {
			newTime = "昨天";
		} else { // 0219
			String a = timeForma.substring(0, 2);
			if ("10".equals(a)||"11".equals(a)||"12".equals(a)) {
				newTime = timeForma.substring(2,4) + a + "月";
			}else {
				newTime = timeForma.substring(2,4) + timeForma.substring(1, 2) + "月";
			}
		}
		return newTime;
	}
	
	
	public static String getTimeForOther(String time) {
		// 根据日期情况显示的指定格式的日期
		String newTime = "";
		//截取当前年的时间格式2012  和当前 日期的时间格式
		String currentDay = getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD_ZH_SLASH);
		String currentYear = currentDay.substring(0, 4);
		//昨天日期格式2012/02/19
		String yesDate= getYesterdaySpecifyFormat(DateUtil.FORMAT_YYYY_MM_DD_ZH_SLASH);
		//先转换格式：2012/02/19
		String timeForma = getNewFormatDateString(time, FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, FORMAT_YYYY_MM_DD_ZH_SLASH);
		//今年
		if (timeForma.contains(currentYear)) {
			if (timeForma.contains(currentDay)) {//当天  05:11 
				newTime = getNewFormatDateString(time,FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, FORMAT_HH_MM);
			}else if (timeForma.contains(yesDate)) {//昨天
				newTime = "昨天";
			}else {// 当年 02/19
				newTime = timeForma.substring(5);
			}
		}else {//跨年全部显示
			newTime = timeForma;
		}
		return newTime;
	}
	
	
	/**
	 * @Description: (参考界面) 根据日期的情况获取指定格式的时间
	 * 	参考时间：今天  xx : xx， 当年 xx-xx xx:xx,  跨年 xxxx-xx-xx xx : xx
	 * @param time 格式为：yyyy年MM月dd日HH时mm分ss秒
	 * @return: String
	 */
	public static String getTimeForReference (String time) {
		// 根据日期情况显示的指定格式的日期
		String newTime;
		//截取当前年的时间格式2012  和当前 日期的时间格式
		String currentTimeStr = getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD_HH_MM);
		String currentYear = currentTimeStr.substring(0, 4);
		String currentDay = currentTimeStr.substring(0, 10);
		//先转换格式：2012-02-19 05:11 
		time = DateUtil.getNewFormatDateString(time, DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH, DateUtil.FORMAT_YYYY_MM_DD_HH_MM);
		//今年
		if (time.contains(currentYear)) {
			//当天  05:11 
			if (time.contains(currentDay)) {
				newTime = time.substring(11);
			} else {//当年 02-19 05:11
				newTime = time.substring(5);
			}
		}else {//跨年全部显示
			newTime = time;
		}
		return newTime;
	}
	
	/**
	 * @Description: 获取当前时间的固定格式的日期
	 * 1、参考时间：今天  xx : xx
	 * @return: String
	 */
	public static String getFormatTime() {
		//获取当前事件格式2012-02-19 05:11 
		String currentTimeStr = getCurrentTimeSpecifyFormat(FORMAT_YYYY_MM_DD_HH_MM);
		return getNewFormatDateString(currentTimeStr,FORMAT_YYYY_MM_DD_HH_MM, FORMAT_HH_MM);
	}
	
	/**
	 * @Description:将时间转化为  05：01：10
	 * @param milliSecondTime
	 * @return
	 * @return: String
	 */
	public static String calculatTime(int milliSecondTime) {
		int hour = milliSecondTime / (60 * 60 * 1000);
		int minute = (milliSecondTime - hour * 60 * 60 * 1000) / (60 * 1000);
		int seconds = (milliSecondTime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		if (seconds >= 60) {
			seconds = seconds % 60;
			minute += seconds / 60;
		}
		if (minute >= 60) {
			minute = minute % 60;
			hour += minute / 60;
		}

		String sh = "";
		String sm = "";
		String ss = "";
		if (hour < 10) {
			sh = "0" + String.valueOf(hour);
		} else {
			sh = String.valueOf(hour);
		}
		if (minute < 10) {
			sm = "0" + String.valueOf(minute);
		} else {
			sm = String.valueOf(minute);
		}
		if (seconds < 10) {
			ss = "0" + String.valueOf(seconds);
		} else {
			ss = String.valueOf(seconds);
		}
		return sh + ":" + sm + ":" + ss;
	}
	
	/**
	 * 
	 * @Description: 把事件字符串一指定的格式输出
	 * @param time 原事件字符串
	 * @param format 格式
	 * @return
	 * @return: String 返回的结果格式    13*4 (13号4月) 今天|昨天
	 */
	public static String getFormatDateString(String time,String format) {
		StringBuffer sb = new StringBuffer();
		// 过滤昨天和今天信息
		if ("今天".equals(time) || "昨天".equals(time)) {
			sb.append(time);
			return sb.toString();
		}
		
	    Date d = null;  
	    SimpleDateFormat sdf = new SimpleDateFormat(format);  
	    
	    try {
			d = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				Calendar calendar = Calendar.getInstance();  
				calendar.setTime(d);
				
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if(day / 10 == 0) {
					sb.append("0");
				}
				sb.append(day);
				// 特殊符号用来分隔月份和天
				sb.append("*"); 
				sb.append(1 + calendar.get(Calendar.MONTH));
			}
		}
		return sb.toString();
	}
	/**
	 * @Description: 根据年月日比较两个时间是否相等
	 * @param srcDate 
	 * @param oldDate
	 * @return
	 * @return: boolean 如果两个时间的年月日都相等则返回true 否则返回false
	 */
	public static boolean compareDateByYMD(String srcDate,String oldDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH);  
		Date tempDate1 = null;  
		Date tempDate2 = null;  
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		try {
			tempDate1 = sdf.parse(srcDate);
			tempDate2 = sdf.parse(oldDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (tempDate1 != null && tempDate2 != null) {
				calendar1.setTime(tempDate1);
				calendar2.setTime(tempDate2);
				return isTheSameDay(calendar1,calendar2);
			}
		}
		return false;
	}
	/**
	 * @Description: 判断当前时间是否是昨天或者是今天
	 * @param time 要判断的时间
	 * @return
	 * @return: String 返回today，yesterday or null
	 */
	public static String isTodayOrYestorday(String time) {
		String resultTime = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD_HHMMSS_WORD_ZH); 
		Date d = null;
		Calendar calendar1 = null;
		Calendar calendar2 = null;
		try {
			d = sdf.parse(time); 
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				calendar1 = Calendar.getInstance();
				calendar1.setTime(new Date(System.currentTimeMillis()));
				calendar2 = Calendar.getInstance();
				calendar2.setTime(d);
				if (isTheSameDay(calendar1,calendar2)) {
					resultTime = "今天";
				}else {
					// 判断是否为昨天
					calendar1.add(Calendar.DAY_OF_MONTH, -1);
					if (isTheSameDay(calendar1,calendar2)) {
						resultTime = "昨天";
					}
				}
			}
		}
		return resultTime;
	}
	
	/**
	 * @Description: 判断两个时间的年月日是否相等
	 * @param calendar1
	 * @param calendar2
	 * @return
	 * @return: boolean 相等则返回true 否则返回false
	 */
	public static boolean isTheSameDay(Calendar calendar1,Calendar calendar2) {
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
			   calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
			   calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}
	


}
