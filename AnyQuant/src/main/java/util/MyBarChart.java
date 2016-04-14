package util;

import java.util.Map;
import java.util.Map.Entry;

import org.python.antlr.PythonParser.return_stmt_return;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;
import jnr.ffi.Struct.int16_t;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	private Map<String , Integer>  data;
	private Series<String, Number> dataSeries;


	public MyBarChart() {

	}


	public void addData(Series series) {
		barchart.getData().clear();
		barchart.getData().add(series);
	}

	public void addData(){

		barchart.setAnimated(false);


		Timeline timeline = new Timeline();
		KeyValue[] values = new KeyValue[data.size()];
		for(int i=0;i<values.length;i++){
			XYChart.Data oneData = dataSeries.getData().get(i);
			values[i] = new KeyValue(oneData.YValueProperty(), (double)data.get(oneData.getXValue()));
		}

		KeyFrame frame = new KeyFrame(new Duration(1000), values);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}



	public static BarChart<String, Number> createBarChart(){
		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		 BarChart< String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
		return chart;
	}


	public BarChart<String, Number> createBarChart(Map<String , Integer>  data){
		this.data=data;
		init();
		return this.barchart;
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		yAxis.setUpperBound(getMax()+1);
		yAxis.setAutoRanging(false);
		this.barchart = new BarChart<String, Number>(xAxis, yAxis);
		this.barchart.setAnimated(false);
		this.dataSeries = new XYChart.Series<String,Number>();


		for(Entry<String, Integer> entry : this.data.entrySet()){
        	dataSeries.getData().add(new XYChart.Data(entry.getKey(),0));
        }

		this.barchart.getData().add(dataSeries);
		this.barchart.setAnimated(true);


	}


	private int getMax(){
		int max =0;
		for(Entry<String, Integer> entry: data.entrySet()){
			   if(entry.getValue()>max){
				   max=entry.getValue();
			   }
		}
		return max;
	}

	public BarChart getBarChart() {
		return barchart;
	}

}

