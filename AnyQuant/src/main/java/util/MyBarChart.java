package util;

import java.util.Map;
import java.util.Map.Entry;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;

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
//		for(Entry<String, Integer> entry : this.data.entrySet()){
//        	//this.dataSeries.getData().add(new XYChart.Data(entry.getKey(),entry.getValue()));
//			for(XYChart.Data oneData:this.dataSeries.getData()){
//
//			}
//		}
		barchart.setAnimated(false);
//		for(XYChart.Data oneData:this.dataSeries.getData()){
//			oneData.setYValue(data.get(oneData.getXValue()));
//		}

		Timeline timeline = new Timeline();
		KeyValue[] values = new KeyValue[data.size()];
		for(int i=0;i<values.length;i++){
			XYChart.Data oneData = dataSeries.getData().get(i);
			values[i] = new KeyValue(oneData.YValueProperty(), data.get(oneData.getXValue()));
		}

		KeyFrame frame = new KeyFrame(new Duration(1000), values);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

	public void Animate(){

        barchart.setAnimated(false);
		XYChart.Series zeroSeries = new XYChart.Series();
		for(Entry<String, Integer> entry : this.data.entrySet()){
        	zeroSeries.getData().add(new XYChart.Data(entry.getKey(),0));
        }
	    this.barchart.getData().add(zeroSeries);

	    barchart.setAnimated(true);
		for(Entry<String, Integer> entry : this.data.entrySet()){
        	zeroSeries.getData().add(new XYChart.Data(entry.getKey(),entry.getValue()));
        }

	}

	public BarChart<String, Number> createBarChart(Map<String , Integer>  data){
		this.data=data;
		init();
		return this.barchart;
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
	//	yAxis.setUpperBound(4);
	//	yAxis.setAutoRanging(false);
		this.barchart = new BarChart<String, Number>(xAxis, yAxis);
		this.barchart.setAnimated(false);
		this.dataSeries = new XYChart.Series<String,Number>();


		for(Entry<String, Integer> entry : this.data.entrySet()){
        	dataSeries.getData().add(new XYChart.Data(entry.getKey(),0));
        }

		this.barchart.getData().add(dataSeries);
		this.barchart.setAnimated(true);

//		this.barchart.setAnimated(false);
//		barchart.setLegendVisible(false);
//		barchart.setOnMouseMoved(
//		new EventHandler<MouseEvent>(){
//            @Override public void handle(MouseEvent e){
////              System.out.println(e.getX());
//            	String x_=xAxis.getValueForDisplay(e.getX());
//            	barchart.getData().get(0);
////            	for(XYChart.Data t:barchart.getData()){
//
////            	}
////            	System.out.println(barchart.getData().);
//            }
//          });

	}

	public BarChart getBarChart() {
		return barchart;
	}

}

