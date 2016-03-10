package ui.tool;

import java.awt.Container;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import vo.TimeSharingVO;

/**
*根据给定的数据集画出折线图，并加到指定的panel上
*@author:duanzhengmou
*@date:2016年3月11日
*/
public class LineChart {
	JFreeChart chart;
	ChartPanel cp ;
	String rowKey = "RowKey";
	String columnKey = "columnKey";
	TimeSeries timeSeries;
	TimeSeriesCollection timeSeriesCollection;
	public LineChart(Container container,Iterator<TimeSharingVO> dataItr) {
		
		chart = ChartFactory.createTimeSeriesChart("title", "categoryAxisLabel", "valueAxisLabel", TranslateData(dataItr));
		cp = new ChartPanel(chart,false);
		cp.setBounds(0, 0, 300, 200);
		container.add(cp);
	}
	private XYDataset TranslateData(Iterator<TimeSharingVO> dataItr){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		timeSeriesCollection = new TimeSeriesCollection();
		timeSeries = new TimeSeries("name");
		
		
		int counter=1;
		int month = 1;
		int year = 2016;
		while(dataItr.hasNext()){
			System.out.println(month+" "+counter);
//			timeSeries.add(new Month(counter,2016), dataItr.next());
			TimeSharingVO temp = dataItr.next();
			Minute min = new Minute(temp.nowTime.getMin(), 
								    temp.nowTime.getHour(), 
								    temp.nowTime.getDay(), 
								    temp.nowTime.getMonth(), 
								    temp.nowTime.getYear());
			timeSeries.add(min, temp.nowPrice);
//			dataset.addValue(dataItr.next(), rowKey, ""+counter);
		}
		timeSeriesCollection.addSeries(timeSeries);
		return  timeSeriesCollection;
	}
	public void setBounds(int x,int y,int width,int height){
		cp.setBounds(x, y, width, height);
	}
	public void setTitle(String title){
		chart.setTitle(title);
		chart.setAntiAlias(true);
	}
	public void addData(TimeSharingVO timesharingVO){
		
		
	}
	
}
