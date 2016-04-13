package util;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import vo.Stock;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	XYChart.Series series;
	final static String itemA = "A";
	final static String itemB = "B";
	final static String itemC = "F";

	public MyBarChart(Stock stock) {
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

}
