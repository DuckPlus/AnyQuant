package ui;

/**

 * Copyright (c) 2008, 2011 Oracle and/or its affiliates.

 * All rights reserved. Use is subject to license terms.

 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;
import ui.controller.MyPieChart;



/**

 * An advanced pie chart with a variety of actions and settable properties.

 *

 * @see javafx.scene.chart.PieChart

 * @see javafx.scene.chart.Chart

 */

public class nettest extends Application {

    private int itemNameIndex = 1;



    private void init(Stage primaryStage) {

//        Group root = new Group();
PieChart pc=MyPieChart.createPieChart();

        Scene scene=new Scene(pc,800,600);
        primaryStage.setScene(scene);
        ObservableList<Data> items= FXCollections.observableArrayList(

                new PieChart.Data("Sun", 20),

                new PieChart.Data("IBM", 12),

                new PieChart.Data("HP", 25),

                new PieChart.Data("Dell", 22),

                new PieChart.Data("Apple", 30)

            );
        MyPieChart.addAllData(pc, items);
        MyPieChart.InitPieChart(pc);
//        pc.getData().addAll(items);
//        root.getChildren().add(createChart());

    }



    protected PieChart createChart() {

        final PieChart pc = new PieChart();


        // setup chart

        pc.setId("BasicPie");

        pc.setTitle("Pie Chart Example");

        return pc;

    }



    @Override public void start(Stage primaryStage) throws Exception {

        init(primaryStage);

        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }
/*
 * package ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class nettest extends Application {

    final static String itemA = "A";
    final static String itemB = "B";
    final static String itemC = "F";
    @Override
    public void start(Stage stage) {
        final NumberAxis yAxis = new NumberAxis();
        final CategoryAxis xAxis = new CategoryAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis, yAxis);
        bc.setTitle("Summary");
        yAxis.setLabel("Value");
//        yAxis.setTickLabelRotation(90);
        xAxis.setLabel("Item");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(itemA,2));
        series1.getData().add(new XYChart.Data( itemB,20));
        series1.getData().add(new XYChart.Data(itemC,10));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(itemA,50));
        series2.getData().add(new XYChart.Data(itemB,41));
        series2.getData().add(new XYChart.Data(itemC,45));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data( itemA,45));
        series3.getData().add(new XYChart.Data(itemB,44));
        series3.getData().add(new XYChart.Data(itemC,88));

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


 */
}