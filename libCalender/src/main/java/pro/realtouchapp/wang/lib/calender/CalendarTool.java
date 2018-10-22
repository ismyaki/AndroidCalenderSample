package pro.realtouchapp.wang.lib.calender;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarTool {
	
	private static Calendar calStartDate = Calendar.getInstance();
	
	/**
	 * 取得指定日期月份資料
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @author Wang
	 * @date 2015/8/17 下午4:40:59
	 * @version 
	 */
	public static ArrayList<java.util.Date> getMonthDates(int year, int month, int date) {
		calStartDate.set(year, month-1, date);
		return getMonthDates();
	}
	
	/**
	 * 取得當月份資料
	 * @return
	 * @author Wang
	 * @date 2015/8/17 下午4:41:14
	 * @version 
	 */
	public static ArrayList<java.util.Date> getThisMonthDates(){
		calStartDate = Calendar.getInstance();
		return getMonthDates();
	}
	
	private static ArrayList<java.util.Date> getMonthDates() {
		UpdateStartDateForMonth();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

		for (int i = 1; i <= 42; i++) {
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}
	
	// 根据改变的日期更新日历
	// 填充日历控件用
	private static void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		
		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, - iDay);

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}
	
	/**
	 * 取得指定日期 周資料
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @author Wang
	 * @date 2015/8/17 下午4:41:55
	 * @version 
	 */
	public static ArrayList<java.util.Date> getWeekDates(int year, int month, int date) {
		calStartDate.set(year, month-1, date);
		return getWeekDates();
	}
	
	/**
	 * 取本真資料
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 * @author Wang
	 * @date 2015/8/17 下午4:41:57
	 * @version 
	 */
	public static ArrayList<java.util.Date> getThisWeekDates() {
		calStartDate = Calendar.getInstance();
		return getWeekDates();
	}
	
	private static ArrayList<java.util.Date> getWeekDates() {
		UpdateStartDateForWeek();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

		for (int i = 1; i <= 7; i++) {
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}
	
	private static void UpdateStartDateForWeek() {
		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, - iDay);

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}
	
}
