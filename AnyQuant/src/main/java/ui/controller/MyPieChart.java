package ui.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author:duanzhengmou
 * @date:Apr 9, 2016
 */
public class MyPieChart {
	private ObservableList<Data> data = FXCollections.observableArrayList();
	private int width = 500, height = 500;

	public MyPieChart() {

	}

	public static PieChart createPieChart(){
		int width = 500, height = 500;
		PieChart piechart = new PieChart();
		piechart.setMaxSize(width, height);
		piechart.setMinSize(width, height);
		piechart.setPrefSize(width, height);
		piechart.setLabelLineLength(10);
		piechart.setLegendSide(Side.RIGHT);
		piechart.getStylesheets().add("ui/source/css/pieChart.css");
		return piechart;
	}

	public static PieChart createPieChart(ObservableList<Data> items){
		int width = 500, height = 500;
		PieChart piechart = new PieChart();
		piechart.setMaxSize(width, height);
		piechart.setMinSize(width, height);
		piechart.setPrefSize(width, height);
		piechart.setLabelLineLength(10);
		piechart.setLegendSide(Side.RIGHT);
		piechart.getStylesheets().add("ui/source/css/pieChart.css");
		piechart.getData().addAll(items);
		init(piechart);
		return piechart;
	}


	public static void addAllData(PieChart piechart,ObservableList<Data> items) {
		piechart.getData().addAll(items);
	}

	public static void init(PieChart piechart) {

		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");
		for (final PieChart.Data data : piechart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					System.out.println("该输出扇形图数据啦！");// TODO 迷！！！！不显示数据！
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
		piechart.getData().stream().forEach(pieData -> {
			pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
				Bounds b1 = pieData.getNode().getBoundsInLocal();
				double newX = (b1.getWidth()) / 2 + b1.getMinX();
				double newY = (b1.getHeight()) / 2 + b1.getMinY();
				// Make sure pie wedge location is reset
				pieData.getNode().setTranslateX(0);
				pieData.getNode().setTranslateY(0);
				TranslateTransition tt = new TranslateTransition(Duration.millis(1500), pieData.getNode());
				tt.setByX(newX);
				tt.setByY(newY);
				tt.setAutoReverse(true);
				tt.setCycleCount(2);
				tt.play();
			});
		});
	}


}
