package util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 *
 * @author ss14
 *
 */
public class DateCalculator {

	private static int[] MONTH_DAY = {31,28 , 31, 30 , 31, 30 ,31 ,31 , 30 , 31 ,30 ,31};


	/**
	 * 返回当前时期包括时间
	 *
	 * @return
	 */
	public static MyDate getToDay() {
		Calendar calendar = Calendar.getInstance();
		MyDate date = DateCalculator.CreateMyDate(calendar);
		return date;
	}

	/**
	 * 以某个日期为基准，偏移量的单位为天
	 *
	 * @param date
	 * @param offset
	 * @return
	 */
	public static MyDate getAnotherDay(MyDate date, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(date.getYear(), date.getMonth() - 1, date.getDay());
		calendar.add(Calendar.DAY_OF_MONTH, offset);
		MyDate date1 = DateCalculator.CreateMyDate(calendar);
		return date1;
	}

	/**
	 * 参数为距离今天的天数偏移量，-1表示昨天，1表示明天
	 *
	 * @param offset
	 * @return
	 */
	public static MyDate getAnotherDay(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, offset);
		MyDate date = DateCalculator.CreateMyDate(calendar);
		return date;
	}

	private static MyDate CreateMyDate(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		MyDate date = new MyDate(year, month, day, hour, min, second);
		return date;
	}

	/**
	 * @author dsn 判断A是否早于B 等于或晚于则返回false
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static Boolean ifEarlier(MyDate dateA, MyDate dateB) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date a = new Date();
		Date b = new Date();
		try {
			a = df.parse(dateA.AllToString());
			b = df.parse(dateB.AllToString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return a.before(b);
	}

	/**
	 * @author dsn 判断两个日期是否相等
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static Boolean ifSame(MyDate dateA, MyDate dateB) {
		if (!ifEarlier(dateA, dateB) && !ifEarlier(dateB, dateA)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某日期是不是周末
	 *
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(MyDate date) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date bdate;
		try {
			bdate = format1.parse(date.DateToString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(bdate);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				return true;
			}
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 返回参数日期之前的第一个工作日
	 *
	 * @param date
	 * @return
	 */
	public static MyDate getFirstPreWorkDay(MyDate date) {
		int i = -1;
		boolean bool = DateCalculator.isWeekend(DateCalculator.getAnotherDay(date, i));
		while (bool) {
			i--;
			bool = DateCalculator.isWeekend(DateCalculator.getAnotherDay(date, i));
		}
		return DateCalculator.getAnotherDay(date, i);
	}


	public static final MyDate getMondayofTheWeek(MyDate date) {

		Calendar calendar = getMyCalendar(date);
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -6);
		} else {
			calendar.add(Calendar.DATE, 2 - calendar.get(Calendar.DAY_OF_WEEK));

		}

		return new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
	}

	public static final MyDate getFridayofTheWeek(MyDate date) {

		Calendar calendar = getMyCalendar(date);
		int nowDay = calendar.get(Calendar.DAY_OF_WEEK);
		if (!(nowDay == 1 || nowDay >= 6)) {
			calendar.add(Calendar.DATE, Calendar.FRIDAY - calendar.get(Calendar.DAY_OF_WEEK));
		}
		return new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
	}

	public static final Calendar getMyCalendar(MyDate date) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(date.getYear(), date.getMonth() - 1, date.getDay());
		return calendar;
	}

	public static void main(String[] args) {
//		System.out.println(DateCalculator.getToDay().DateToString());
//		System.out.println(getMondayofTheWeek(DateCalculator.getToDay()).DateToString());
//
//		Calendar c2 = Calendar.getInstance();
//		c2.clear();
//		c2.set(2016, 2, 7);
//		// 年份
//		int year = c2.get(Calendar.YEAR);
//		// 月份
//		int month = c2.get(Calendar.MONTH) + 1;
//		// 日期
//		int date = c2.get(Calendar.DATE);
//		// 小时
//		int hour = c2.get(Calendar.HOUR_OF_DAY);
//		// 分钟
//		int minute = c2.get(Calendar.MINUTE);
//		// 秒
//		int second = c2.get(Calendar.SECOND);
//		// 星期几
//		int day = c2.get(Calendar.DAY_OF_WEEK);
//		System.out.println("年份：" + year);
//		System.out.println("月份：" + month);
//		System.out.println("日期：" + date);
//		System.out.println("小时：" + hour);
//		System.out.println("分钟：" + minute);
//		System.out.println("秒：" + second);
//		System.out.println("星期：" + day);
//
//		c2.add(Calendar.DATE, -6);
//		// 年份
//		year = c2.get(Calendar.YEAR);
//		// 月份
//		month = c2.get(Calendar.MONTH) + 1;
//		// 日期
//		date = c2.get(Calendar.DATE);
//		// 小时
//		hour = c2.get(Calendar.HOUR_OF_DAY);
//		// 分钟
//		minute = c2.get(Calendar.MINUTE);
//		// 秒
//		second = c2.get(Calendar.SECOND);
//		// 星期几
//		day = c2.get(Calendar.DAY_OF_WEEK);
//		System.out.println("年份：" + year);
//		System.out.println("月份：" + month);
//		System.out.println("日期：" + date);
//		System.out.println("小时：" + hour);
//		System.out.println("分钟：" + minute);
//		System.out.println("秒：" + second);
//		System.out.println("星期：" + day);

		java.sql.Date sqlDate = java.sql.Date.valueOf("2016-3-20");
		MyDate myDate = SQLDateToMyDate(sqlDate);
		System.out.println("MYDATE:"+myDate.DateToString());



	}

	public static boolean isLeapYear(MyDate date) {
		int year = date.getYear();
		return (year % 4 == 0) ? ((year % 100 == 0) ? ((year % 400 == 0) ? true : false) : true) : false;
	}

	public static int getMonthEndDay(MyDate date){

			if(date.getMonth() != 2){
				return MONTH_DAY[date.getMonth() - 1];
			}else{
				if(isLeapYear(date)){
					return 29;
				}else{
					return MONTH_DAY[1];
				}
			}





	}


	public static java.sql.Date MyDateToSQLDate(MyDate myDate){
		java.sql.Date date = java.sql.Date.valueOf(myDate.DateToString());
		return date;
	}

	public static MyDate SQLDateToMyDate(java.sql.Date sqlDate){
		if (sqlDate == null) {
			return new MyDate(2014,1,1);
		}
		MyDate myDate = MyDate.getDateFromString(sqlDate.toString());
		return myDate;
	}

	public static void getNextMonth(MyDate date) {
		if (date.getMonth() == 12) {
			date.setMonth(1);
			date.setYear(date.getYear() + 1);
		} else {
			date.setMonth(date.getMonth() + 1);
		}
	}

}
