package ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import util.MyBarChart;

public class demo extends Application {

    final static String itemA = "A";
    final static String itemB = "B";
    final static String itemC = "F";
    @Override
    public void start(Stage stage) {
    	  XYChart.Series series1 = new XYChart.Series();
          series1.setName("2003");
          series1.getData().add(new XYChart.Data(itemA,2));
          series1.getData().add(new XYChart.Data( itemB,20));
          series1.getData().add(new XYChart.Data(itemC,10));

    	MyBarChart barCreator=new MyBarChart();
//    	barCreator.addData("2010", 35);
        BarChart<String, Number> barchart=barCreator.getBarChart();

        Scene scene = new Scene(barchart, 800, 600);
        barCreator.addData(series1);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

