package util;

import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 *
 * @author:duanzhengmou,dsn,ss
 * @date:Apr 9, 2016
 */
public class MyPieChart {
	private static final int width = 500, height = 500;
    private PieChart pieChart;
    private Map<String, ? extends Number> valueMap;

	public MyPieChart() {

	}



	public  PieChart createPieChart(Map<String, Integer> map){
		 this.pieChart = new PieChart();
		 this.valueMap=map;
		 this.InitPieChart();
		 return this.pieChart;
	}

	private  void InitPieChart() {
		pieChart.setMaxSize(width, height);
		pieChart.setMinSize(width, height);
		pieChart.setPrefSize(width, height);
		pieChart.setLayoutX(100);
		pieChart.setLayoutY(100);

		pieChart.setLabelLineLength(10);
//		pieChart.setLegendSide(Side.RIGHT);
//		pieChart.getStylesheets().add("ui/source/css/pieChart.css");


		final Label caption = new Label("");
		caption.setTextFill(Color.WHITE);
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
    //	boolean isFirst = true;
		ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

		if (valueMap.size() > 1) {
			for (String key : valueMap.keySet()) {
				chartData.add(new PieChart.Data(key, 100.0/valueMap.size()));
			}
		}else{

			for (String key : valueMap.keySet()) {
				chartData.add(new PieChart.Data(key, valueMap.get(key).doubleValue()));
			}
		}

		pieChart.setData(chartData);
		//setToolTip();
		KeyValue[] keyValues = new KeyValue[valueMap.size()];

		for (int i = 0; i < keyValues.length; i++) {

			PieChart.Data oneData = chartData.get(i);

			keyValues[i] = new KeyValue(oneData.pieValueProperty(), valueMap.get(oneData.getName()));
		}

		KeyFrame frame = new KeyFrame(new Duration(1500), keyValues);
		Timeline timeline = new Timeline(frame);
		timeline.play();
    }

	private  void  extensionAnimation(){
		pieChart.getData().stream().forEach(pieData -> {
			pieData.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
				Bounds b1 = pieData.getNode().getBoundsInLocal();
				double newX = b1.getWidth() / 2 + b1.getMinX();
				double newY = b1.getHeight() / 2 + b1.getMinY();
				// Make sure pie wedge location is reset
				pieData.getNode().setTranslateX(0);
				pieData.getNode().setTranslateY(0);
				TranslateTransition tt = new TranslateTransition(Duration.millis(500), pieData.getNode());
				tt.setByX(newX*0.2);
				tt.setByY(newY*0.2);
				tt.setAutoReverse(true);
				tt.setCycleCount(2);
				tt.play();
			});
		});
	}




}
