package ui.tool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
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
	double preData = 0;
	public LineChart(Container container,Iterator<TimeSharingVO> dataItr) {
		
		chart = ChartFactory.createTimeSeriesChart("title", "categoryAxisLabel", "valueAxisLabel", TranslateData(dataItr));
		cp = new ChartPanel(chart,false);
		cp.setBounds(0, 0, 300, 200);
		container.add(cp);
		DrawPreData(preData);
	}
	/**
	 * 
	 * @param dataItr
	 * @return
	 */
	private XYDataset TranslateData(Iterator<TimeSharingVO> dataItr){
		timeSeriesCollection = new TimeSeriesCollection();
		timeSeries = new TimeSeries("name");
		
		while(dataItr.hasNext()){
			TimeSharingVO temp = dataItr.next();
			Minute min = new Minute(temp.nowTime.getMin(), 
								    temp.nowTime.getHour(), 
								    temp.nowTime.getDay(), 
								    temp.nowTime.getMonth(), 
								    temp.nowTime.getYear());
			timeSeries.add(min, temp.nowPrice);
			preData = temp.preClose;
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
		Minute min = new Minute(timesharingVO.nowTime.getMin(), 
			    timesharingVO.nowTime.getHour(), 
			    timesharingVO.nowTime.getDay(), 
			    timesharingVO.nowTime.getMonth(), 
			    timesharingVO.nowTime.getYear());
		timeSeries.add(min,timesharingVO.nowPrice);
		timeSeriesCollection.removeAllSeries();
		timeSeriesCollection.addSeries(timeSeries);
	}
	
	private void DrawPreData(double value){
		ValueMarker valuemarker = new ValueMarker(value);
		valuemarker.setPaint(Color.red);
		System.out.println(value);
		valuemarker.setStroke(new BasicStroke(1.0F, 1, 1, 1.0F, new float[] {13F, 8F}, 0.0F));
//		valuemarker.setStroke(new BasicStroke(2.0F));
		chart.getXYPlot().addRangeMarker(valuemarker);
	}
	
}
