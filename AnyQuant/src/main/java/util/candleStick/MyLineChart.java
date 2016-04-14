package util.candleStick;
/**
*
*@author:duanzhengmou
*@date:Apr 9, 2016
*/

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

import enumeration.CmpChartType;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import ui.controller.CmpChartRecord;
import vo.Stock;


public class MyLineChart {
	private Mytime beginTime = new Mytime("6", "30");
	private XYChart.Series<String,Number> series;
	private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();
	private LineChart<String,Number> lineChart ;
	private LinkedList<CmpChartRecord>seriesKeeper = new LinkedList<CmpChartRecord>();
	private LinkedList<Series<String, Number>>Allseries = new LinkedList<Series<String, Number>>();
	public MyLineChart() {
		// TODO Auto-generated constructor stub
		init();
	}
	//==========================


	   public void init(){
//	        xAxis.setLabel("xAxis");
	        yAxis.setAutoRanging(false);


	        //defining a series
	        series = new XYChart.Series<String,Number>();
//	        series.setName("series_name");
	        //creating the chart
	        //set basic property of chart

	        lineChart  = new LineChart<String,Number>(xAxis,yAxis);
	        lineChart.setLegendVisible(true);
	        lineChart.setTitle("chart title");// the title depend on code name
//	        lineChart.getData().add(series);
	        lineChart.setCreateSymbols(false);

	    }
	   /**
	    * 自选比较 专用！
	    */
	   public void addSeries(Series<String, Number> series,Stock stock,CmpChartType type){
		   if(seriesExist(stock, type)){
			   return;
		   }
		   series.setName(stock.name.get()+"-"+type.toString().replace("Chart", ""));
		   lineChart.getData().add(series);
		   seriesKeeper.add(new CmpChartRecord(type, stock.code.get(), series));
		   Allseries.add(series);
	   }
	   public void removeAllSeries(){
			   System.out.println("remove");
			   lineChart.getData().removeAll(Allseries);
			   Allseries.clear();
			   seriesKeeper.clear();
	   }
	    private boolean seriesExist(Stock stock,CmpChartType type){
	    	Iterator<CmpChartRecord>recordItr = seriesKeeper.iterator();
	    	while(recordItr.hasNext()){
	    		CmpChartRecord temp = recordItr.next();
	    		System.out.println(temp.getStockCode()+"  "+stock.code.get()+"  "+temp.getType()+"  "+type);
	    		if(temp.getStockCode()==stock.code.get()&&temp.getType()==type){
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	   
	   /**
	    * 通用方法
	    */
	   public void setSize(int width,int height){
		   lineChart.setPrefHeight(height);
	       lineChart.setPrefWidth(width);
	   }
	   public LineChart<String, Number> getTimesharingChart(){
		   return lineChart;
	   }
	   public void setChartProperty(String stockName,double UpperBounds,double LowerBounds){
		   lineChart.setTitle(stockName+" Monitoring");
		   yAxis.setLowerBound(LowerBounds);
		   yAxis.setUpperBound(UpperBounds);
	   }
	   public void addData(String time,Double value){
		   series.getData().add(new XYChart.Data<String,Number>(time,value));
	   }

	    private void toCurrentTime(){
	    	int currentHour = LocalDateTime.now().getHour();
	    	int currentMinute = LocalDateTime.now().getMinute();
	    	double x = 0;
//	    	System.out.println("current"+LocalDateTime.now().getHour()+LocalDateTime.now().getMinute());
//	    	System.out.println("begin"+beginTime.hour+"  "+beginTime.minute);
	    	while(Integer.valueOf(beginTime.hour)!=currentHour||Integer.valueOf(beginTime.minute)!=currentMinute){
	    	series.getData().add(new XYChart.Data<String,Number>(beginTime.getMS(),Math.sin(x+=0.05)));
	    	beginTime.addminute();
	    	}
	    }
	//==========================
	    public NumberAxis getYAxis(){
	    	return yAxis;
	    }

}
	 

class Mytime {
	String hour;
	String minute;

	Mytime(String hour,String minute){
		this.hour = hour;
		this.minute = minute;
	}
	public String getMS(){
		return hour+":"+minute;
	}
	public void addminute(){
		if(Integer.valueOf(minute)<60){
			minute = String.valueOf(Integer.valueOf(minute)+1);
		}else{
			minute = "00";
			hour = String.valueOf(Integer.valueOf(hour)+1);
		}
	}

}
