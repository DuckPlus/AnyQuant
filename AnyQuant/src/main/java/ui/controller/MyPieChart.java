package ui.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
*@author:duanzhengmou
*@date:Apr 9, 2016
*/
public class MyPieChart {
	private ObservableList<Data> items = FXCollections.observableArrayList();
	private PieChart piechart;
	private int width=500,height=500;
	Label caption =new Label();
	public MyPieChart() {
		init();
	}
	public MyPieChart(ObservableList<Data> datas) {
		this.items=datas;
		init();
	}
	public MyPieChart(int width,int height) {
		this.width=width;
		this.height=height;
		init();
	}

	private void init(){
		caption.setTextFill(Color.CHOCOLATE);
		caption.setStyle("-fx-font: 24 arial;");
		piechart = new PieChart(items);
		piechart.setMaxSize(width,height);
		piechart.setMinSize(width,height);
		piechart.setPrefSize(width,height);
		piechart.setLabelLineLength(10);
		piechart.setLegendSide(Side.RIGHT);
		piechart.getStylesheets().add("ui/source/css/pieChart.css");

		 for (final PieChart.Data data : piechart.getData()) {
	            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
	                e -> {
	                    double total = 0;
	                    for (PieChart.Data d : piechart.getData()) {
	                        total += d.getPieValue();
	                    }
	                    caption.setTranslateX(e.getSceneX());
	                    caption.setTranslateY(e.getSceneY());
	                    String text = String.format("%.1f%%", 100*data.getPieValue()/total) ;
	                    caption.setText(text);
	                 }
	                );
	        }
		 piechart.getData().stream().forEach(pieData -> {
	            pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
	                Bounds b1 = pieData.getNode().getBoundsInLocal();
	                double newX = (b1.getWidth()) / 2 + b1.getMinX();
	                double newY = (b1.getHeight()) / 2 + b1.getMinY();
	                // Make sure pie wedge location is reset
	                pieData.getNode().setTranslateX(0);
	                pieData.getNode().setTranslateY(0);
	                TranslateTransition tt = new TranslateTransition(
	                        Duration.millis(1500), pieData.getNode());
	                tt.setByX(newX);
	                tt.setByY(newY);
	                tt.setAutoReverse(true);
	                tt.setCycleCount(2);
	                tt.play();
	            });
	        });
	}

	public PieChart getPieChart(){
		return piechart;
	}

}
