package util;

import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	private Map<String , Integer>  data;

	public MyBarChart() {
		init();
	}


	public void addData(Series series) {
		barchart.getData().add(series);
	}

	public void addData(BarChart bc,Map<String , Integer>  data){
		bc.getData().clear();
		XYChart.Series series = new XYChart.Series();
		for(Entry<String, Integer> entry:data.entrySet()){
        	series.getData().add(new XYChart.Data(entry.getKey(),entry.getValue()));
        }
		bc.getData().add(series);
	}

	public BarChart<String, Number> createBarChart(Map<String , Integer>  data){
		this.data=data;
//		this.barchart.setAnimated(true);
		init();
		return this.barchart;
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		barchart = new BarChart<String, Number>(xAxis, yAxis);

	}

	public BarChart getBarChart() {
		return barchart;
	}

}

