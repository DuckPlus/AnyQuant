package ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;
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
          series1.getData().add(new XYChart.Data("C",2));
          series1.getData().add(new XYChart.Data( "D",20));
          series1.getData().add(new XYChart.Data("E",10));
          series1.getData().add(new XYChart.Data("W",2));
          series1.getData().add(new XYChart.Data("Z",20));
          series1.getData().add(new XYChart.Data("Q",10));
    	MyBarChart barCreator=new MyBarChart();
//    	barCreator.addData("2010", 35);
        BarChart<String, Number> barchart=barCreator.getBarChart();

        
        Line line1 = new Line(20, 40, 270, 40);
        line1.getStrokeDashArray().addAll(25d, 20d, 5d, 20d);
         
        Line line2 = new Line(20, 60, 270, 60);
        line2.getStrokeDashArray().addAll(50d, 40d);
         
        Line line3 = new Line(20, 80, 270, 80);
        line3.getStrokeDashArray().addAll(25d, 10d);
         
        Line line4 = new Line(20, 100, 270, 100);
        line4.getStrokeDashArray().addAll(2d);
         
        Line line5 = new Line(20, 120, 270, 120);
        line5.getStrokeDashArray().addAll(2d, 21d);
         
        
        Scene scene = new Scene(barchart, 800, 600);
        barCreator.addData(series1);
//        barchart.addAll(line1, line2, line3, line4, line5);
        
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

