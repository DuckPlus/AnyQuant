package util;

/**
 * 日期类
 * @author czq,ss
 * @date 2016-3-10
 */
public class MyDate implements Cloneable{
	private int year, month, day, hour, min, second;
	private static final String SPLIT_STRING = "-";

	public MyDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public MyDate(int year, int month, int day, int hour, int min, int second) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.second = second;
	}



	// 返回日期“2015-01-10”
	public String DateToString() {
		String month = "";
		String day = "";
		if (this.month < 10) {
			month = "0" + this.month;
		} else {
			month = "" + this.month;
		}

		if (this.day < 10) {
			day = "0" + this.day;
		} else {
			day = "" + this.day;
		}

		return "" + year + SPLIT_STRING + month + SPLIT_STRING + day;
	}

	// 返回日期“20150110”
	public String DateToStringSimple() {
		String month = "";
		String day = "";
		if (this.month < 10) {
			month = "0" + this.month;
		} else {
			month = "" + this.month;
		}

		if (this.day < 10) {
			day = "0" + this.day;
		} else {
			day = "" + this.day;
		}

		return "" + year  + month  + day;
	}

	// 返回时间“14:11:01”
	public String TimeToString() {
		String hour = "";
		String min = "";
		String second = "";
		if (this.hour < 10) {
			hour = "0" + this.hour;
		} else {
			hour = "" + this.hour;
		}

		if (this.min < 10) {
			min = "0" + this.min;
		} else {
			min = "" + this.min;
		}

		if (this.second < 10) {
			second = "0" + this.second;
		} else {
			second = "" + this.second;
		}

		return hour + ":" + min + ":" + second;

	}

	// 返回日期加时间"2015-01-10 14:11:01"中间是空格
	public String AllToString() {
		return this.DateToString() + " " + this.TimeToString();
	}

	public static MyDate getDateFromString(String date) {
		String[] tmp = date.split(SPLIT_STRING);
		return new MyDate(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]),
				Integer.parseInt(tmp[2]));
	}

	@Override
	public MyDate clone()  {
		try {
			return (MyDate) super.clone();
		} catch (CloneNotSupportedException e) {
			return DateCalculator.getToDay();
		}
	}


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}


}
