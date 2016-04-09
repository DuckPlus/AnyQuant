package ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
/**
*
*@author:duanzhengmou
*@date:Apr 9, 2016
*/
public class MyPieChart {
	private ObservableList<Data> items = FXCollections.observableArrayList();
	private PieChart pc;
	public MyPieChart() {
		init();
	}
	
	private void init(){
		pc = new PieChart(items);
		pc.setPrefSize(200, 200);
	}
	
	public void addData(String s,double value){
		pc.setLegendVisible(true);
		items.add(new PieChart.Data(s, value));
		
	}
	public PieChart getPieChart(){
		return pc;
	}
	
	 protected PieChart createChart() {
		 
	        final PieChart pc = new PieChart(FXCollections.observableArrayList(
	            new PieChart.Data("Sun", 20),
	            new PieChart.Data("IBM", 12),
	            new PieChart.Data("HP", 25),
	            new PieChart.Data("Dell", 22),
	            new PieChart.Data("Apple", 30)
	        ));
	        // setup chart
	        pc.setId("BasicPie");
	        pc.setTitle("Pie Chart Example");
	        return pc;
	    }
}
