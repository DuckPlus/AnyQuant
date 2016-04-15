package util;

import java.util.Map;
import java.util.Map.Entry;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	private Map<String, Integer> data;
	private Series<String, Number> dataSeries;

	public MyBarChart() {

	}

	public void addData(Series series) {
		barchart.getData().clear();
		barchart.getData().add(series);
	}

	public void animate() {

		barchart.setAnimated(false);
		barchart.getYAxis().setAutoRanging(false);
		NumberAxis yAxis = (NumberAxis) barchart.getYAxis();
		yAxis.setUpperBound(getMax() + 1);
		yAxis.setTickUnit(getPreTickUnit());

		for (Entry<String, Integer> entry : data.entrySet()) {
			this.dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), 0));
		}

		Timeline timeline = new Timeline();
		KeyValue[] values = new KeyValue[data.size()];
		for (int i = 0; i < values.length; i++) {
			XYChart.Data oneData = dataSeries.getData().get(i);
			values[i] = new KeyValue(oneData.YValueProperty(), (double) data.get(oneData.getXValue()));
		}

		KeyFrame frame = new KeyFrame(new Duration(500), values);
		timeline.getKeyFrames().add(frame);
		timeline.play();
		timeline.setOnFinished((ActionEvent ae) -> {
			timeline.stop();
		});

	}

	public static BarChart<String, Number> createBarChart() {
		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		BarChart<String, Number> chart = new BarChart<String, Number>(xAxis, yAxis);
		return chart;
	}


	public BarChart<String, Number> createBarChart(Map<String, Integer> data) {
		this.data = data;
		init();
		return this.barchart;
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		this.barchart = new BarChart<String, Number>(xAxis, yAxis);
		this.dataSeries = new XYChart.Series<String, Number>();
		this.barchart.getData().add(dataSeries);

	}

	private double getMax() {
		double max = 0;
		for (Entry<String, Integer> entry : data.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		return max;
	}

	private double getPreTickUnit() {
		double upBounds = getMax() + 1;
		double tempUnit = upBounds / 10.0;
		int tempIntUnit = (int) tempUnit;
		double minorPart = tempUnit - tempIntUnit;
		if (minorPart != 0) {
			minorPart = 0.5;
		}
		return tempIntUnit + minorPart;

	}

	public BarChart getBarChart() {
		return barchart;
	}

}
