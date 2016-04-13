package ui.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	XYChart.Series series;
	final static String itemA = "A";
	final static String itemB = "B";
	final static String itemC = "F";

	public MyBarChart() {
		init();
	}


	public void addData(Series series) {
		this.series=series;
		barchart.getData().add(series);
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		barchart = new BarChart<String, Number>(xAxis, yAxis);
		yAxis.setLabel("数值");
		// yAxis.setTickLabelRotation(90);
		xAxis.setLabel("日期");

		barchart.setTitle("成交量图");


	}

	public BarChart getBarChart() {
		return barchart;
	}

	public void start(Stage stage) {
		//
		//
		// series1.setName("2003");
		// series1.getData().add(new XYChart.Data(itemA,2));
		// series1.getData().add(new XYChart.Data( itemB,20));
		// series1.getData().add(new XYChart.Data(itemC,10));
		//
		// XYChart.Series series2 = new XYChart.Series();
		// series2.setName("2004");
		// series2.getData().add(new XYChart.Data(itemA,50));
		// series2.getData().add(new XYChart.Data(itemB,41));
		// series2.getData().add(new XYChart.Data(itemC,45));
		//
		// XYChart.Series series3 = new XYChart.Series();
		// series3.setName("2005");
		// series3.getData().add(new XYChart.Data( itemA,45));
		// series3.getData().add(new XYChart.Data(itemB,44));
		// series3.getData().add(new XYChart.Data(itemC,88));
		//
		// Scene scene = new Scene(bc, 800, 600);
		// bc.getData().addAll(series1);
		// stage.setScene(scene);
		// stage.show();
		// }
	}
}
