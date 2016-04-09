package ui.controller.candleStick;
/**
*
*@author:duanzhengmou
*@date:Apr 9, 2016
*/

import java.time.LocalDateTime;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TimeSharingChart {
	Mytime beginTime = new Mytime("6", "30");
	XYChart.Series<String,Number> series;
	final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
	LineChart<String,Number> lineChart ;
	public TimeSharingChart() {
		// TODO Auto-generated constructor stub
		init();
	}
	//==========================
	 

	   public void init(){
	        xAxis.setLabel("Number of Month");
	        yAxis.setAutoRanging(false);
	        yAxis.setLowerBound(17);
	        yAxis.setUpperBound(20);
	        yAxis.setTickUnit(0.1);
	        
	        //creating the chart
	        lineChart  = new LineChart<String,Number>(xAxis,yAxis);
	                
	        lineChart.setTitle("Stock Monitoring, 2010");// the title depend on code name
	        //defining a series
	        
	        series = new XYChart.Series<String,Number>();
	        series.setName("time-sharing");
	        //populating the series with data
//	        toCurrentTime();
//	        series.getData().add(new XYChart.Data("21:13", 23));
//	        series.getData().add(new XYChart.Data(2, 14));
//	        series.getData().add(new XYChart.Data(3, 15));
//	        series.getData().add(new XYChart.Data(4, 24));
//	        series.getData().add(new XYChart.Data(5, 34));
//	        series.getData().add(new XYChart.Data(6, 36));
//	        series.getData().add(new XYChart.Data(7, 22));
//	        series.getData().add(new XYChart.Data(8, 45));
//	        series.getData().add(new XYChart.Data(9, 43));
//	        series.getData().add(new XYChart.Data(10, 17));
//	        series.getData().add(new XYChart.Data(11, 29));
//	        series.getData().add(new XYChart.Data(12, 25));
	        
//update method//			series.getData().add(new XYChart.Data<String,Number>(String.valueOf(LocalDateTime.now().getHour())+":"+String.valueOf(LocalDateTime.now().getMinute()) ,10+Math.random()*15));
	        
	        lineChart.getData().add(series);
	        lineChart.setPrefHeight(600);
	        lineChart.setPrefWidth(1000);
	        lineChart.setCreateSymbols(false);
//	        lineChart.set
	    }
	   public LineChart<String, Number> getTimesharingChart(){
		   return lineChart;
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
