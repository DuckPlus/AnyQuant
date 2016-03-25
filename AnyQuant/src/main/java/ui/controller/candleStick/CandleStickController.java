package ui.controller.candleStick;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.text.html.HTML.Tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;


/**
 *
 * @author ss
 * @date 2016年3月24日
 * =。=
 */
public class CandleStickController  implements Initializable {

    @FXML
    private Tab dayTab;
    @FXML
    private Tab weekTab;
    @FXML
    private Tab monthTab;
    @FXML
    private CandleStickChart chart;

    public  CandleStickController (){

    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

//        Button addBT = new Button("添加");
//        addBT.setOnAction(event->{
//        	XYChart.Series<Number,Number> newSeries = new XYChart.Series<>();
//        	newSeries.getData().add(new XYChart.Data<Number,Number>(32, 28,
//        			new CandleStickExtraValues(18, 30, 12, 22)));
//            chart.getData().add(newSeries);
//        });
     //    root.setCenter(chart);
      //   root.setBottom(addBT);
	  selectDay();
    }



  public  void selectDay(){
	     chart =createChart();
         initPane(dayTab, chart,new ScrollPane());
  }

  private void initPane( Tab tab  , Node chartNode, ScrollPane spane ){

	      GridPane gridPane = new GridPane();
	      ColumnConstraints cc = new ColumnConstraints(600,880,900);
	      RowConstraints rc = new RowConstraints(600,650,700);
	      gridPane.getColumnConstraints().add(cc);
	      gridPane.getRowConstraints().add(rc);
          gridPane.add(chartNode, 0, 0);

	      spane.setContent(gridPane);
          tab.setContent(spane);

  }


    // DAY, OPEN, CLOSE, HIGH, LOW, AVERAGE
    private static double[][] data = new double[][]{
            {1,  25, 20, 32, 16, 20},
            {2,  26, 30, 33, 22, 25},
            {3,  30, 38, 40, 20, 32},
            {4,  24, 30, 34, 22, 30},
            {5,  26, 36, 40, 24, 32},
            {6,  28, 38, 45, 25, 34},
            {7,  36, 30, 44, 28, 39},
            {8,  30, 18, 36, 16, 31},
            {9,  40, 50, 52, 36, 41},
            {10, 30, 34, 38, 28, 36},
            {11, 24, 12, 30, 8,  32.4},
            {12, 28, 40, 46, 25, 31.6},
            {13, 28, 18, 36, 14, 32.6},
            {14, 38, 30, 40, 26, 30.6},
            {15, 28, 33, 40, 28, 30.6},
            {16, 25, 10, 32, 6,  30.1},
            {17, 26, 30, 42, 18, 27.3},
            {18, 20, 18, 30, 10, 21.9},
            {19, 20, 10, 30, 5,  21.9},
            {20, 26, 16, 32, 10, 17.9},
            {21, 38, 40, 44, 32, 18.9},
            {22, 26, 40, 41, 12, 18.9},
            {23, 30, 18, 34, 10, 18.9},
            {24, 12, 23, 26, 12, 18.2},
            {25, 30, 40, 45, 16, 18.9},

            {26, 25, 35, 38, 20, 21.4},
            {27, 24, 12, 30, 8,  19.6},
            {28, 23, 44, 46, 15, 22.2},
            {29, 28, 18, 30, 12, 23},
            {30, 28, 18, 30, 12, 23.2},
            {31, 28, 18, 30, 12, 22}
//            //
//            {32, 28, 18, 36, 14, 32.6},
//            {33, 38, 30, 40, 26, 30.6},
//            {34, 28, 33, 40, 28, 30.6},
//            {35, 25, 10, 32, 6,  30.1},
//            {17, 26, 30, 42, 18, 27.3},
//            {18, 20, 18, 30, 10, 21.9},
//            {19, 20, 10, 30, 5,  21.9},
//            {20, 26, 16, 32, 10, 17.9},
//            {21, 38, 40, 44, 32, 18.9},
//            {22, 26, 40, 41, 12, 18.9},
//            {23, 30, 18, 34, 10, 18.9},
//            {24, 12, 23, 26, 12, 18.2},
//            {25, 30, 40, 45, 16, 18.9},
//            {26, 25, 35, 38, 20, 21.4}

    };


    private CandleStickChart createChart() {
    	//X轴，范围0-32，间隔1
        final NumberAxis xAxis = new NumberAxis(0,32,1);
        xAxis.setMinorTickCount(0);
        //Y轴
        final NumberAxis yAxis = new NumberAxis();

        final CandleStickChart candleStickChart = new CandleStickChart(xAxis,yAxis);
        // setup chart
        candleStickChart.setTitle("Custom Candle Stick Chart");
        xAxis.setLabel("Day");
        yAxis.setLabel("Price");
        // add starting data
        XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
        for (int i=0; i< data.length; i++) {
            double[] day = data[i];
            series.getData().add(
                //参数：日期、开盘价
                new XYChart.Data<Number,Number>(day[0],day[1],new CandleStickExtraValues(day[2],day[3],day[4],day[5]))
            );
        }
        // candleStickChart.getData()  return type:ObservableList<XYChart.Series<Number,Number>>
        ObservableList<XYChart.Series<Number,Number>> data = candleStickChart.getData();
        if (data == null) {

            data = FXCollections.observableArrayList(series);
            candleStickChart.setData(data);
        } else {
            candleStickChart.getData().add(series);
        }
        return candleStickChart;
    }



}
