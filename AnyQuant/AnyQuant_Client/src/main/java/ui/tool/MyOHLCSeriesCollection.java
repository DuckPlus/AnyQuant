/**
 *@author dsn
 *@version 2016年3月12日    下午9:20:32
 */

package ui.tool;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

import enumeration.MyDate;

public class MyOHLCSeriesCollection extends OHLCSeriesCollection{

	List<OHLCSeries> series_all=new ArrayList<OHLCSeries>();
	@Override
	public void addSeries(OHLCSeries series) {
		super.addSeries(series);
		series_all.add(series);
		
	}
	public MyDate getDate(int i,int j){
		MyDate date;
		OHLCSeries sery=series_all.get(i);
		Day day=(Day)sery.getPeriod(j);
		date=new MyDate(day.getYear(),day.getMonth(),day.getDayOfMonth());
		return date;
	}
}

