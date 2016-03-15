/**
 *@author dsn
 *@version 2016年3月12日    下午9:38:59
 */

package ui.tool;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import enumeration.MyDate;

public class MyTimeSeriesCollection extends TimeSeriesCollection{

	List<TimeSeries> series_all=new ArrayList<TimeSeries>();
	@Override
	public void addSeries(TimeSeries series) {
		super.addSeries(series);
		series_all.add(series);
		
	}
	public MyDate getDate(int i,int j){
		MyDate date;
		TimeSeries sery=series_all.get(i);
		Day day=(Day)sery.getTimePeriod(j);
		date=new MyDate(day.getYear(),day.getMonth(),day.getDayOfMonth());
		return date;
	}
}

