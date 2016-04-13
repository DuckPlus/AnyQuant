package ui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
 * @author:duanzhengmou,dsn,ss
 * @date:Apr 9, 2016
 */
public class MyPieChart {
	private static final int width = 500, height = 500;
    private PieChart pieChart;
    private ObservableList<Data> datas;

	public MyPieChart() {

	}



	public  PieChart createPieChart(ObservableList<Data> items){
		 this.pieChart = new PieChart();
		 this.datas=items;
		 this.InitPieChart();
		 return this.pieChart;
	}


	private  void InitPieChart() {
		pieChart.setMaxSize(width, height);
		pieChart.setMinSize(width, height);
		pieChart.setPrefSize(width, height);
		pieChart.setLabelLineLength(10);
		pieChart.setLegendSide(Side.RIGHT);
		pieChart.getStylesheets().add("ui/source/css/pieChart.css");


		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");
		for (final PieChart.Data data : pieChart.getData()) {
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

	}

   public void animate(){
	   pieChart.setAnimated(false);
	   initAnimation();
	   extensionAnimation();
   }

    private  void initAnimation(){
//    	boolean isFirst = true;
    	pieChart.getData().addAll(datas);
	//	setToolTip();
		KeyValue[] keyValues = new KeyValue[pieChart.getData().size()];

		for (int i = 0; i < keyValues.length; i++) {
			PieChart.Data oneData = pieChart.getData().get(i);
			keyValues[i] = new KeyValue(oneData.pieValueProperty() , oneData.getPieValue() );
		}

		KeyFrame frame = new KeyFrame(new Duration(20000), keyValues);
		Timeline timeline = new Timeline(frame);
		timeline.play();
    }

	private   void  extensionAnimation(){
		pieChart.getData().stream().forEach(pieData -> {
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
