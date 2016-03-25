package ui.controller.candleStick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author ss
 * @date 2016年3月24日
 *
 * A candlestick chart is a style of bar-chart used primarily to describe price movements of a security, derivative,
 * or currency over time.
 * The Data Y value is used for the opening price and then the close, high and low values are stored in the Data's
 * extra value property using a CandleStickExtraValues object.
 */

 class CandleStickChart extends XYChart<String,Number> {

	//TODO
     // -------------- CONSTRUCTORS ----------------------------------------------
     /**
      * Construct a new CandleStickChart with the given axis.
      *
      * @param xAxis The x axis to use
      * @param yAxis The y axis to use
      */
     public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
         super(xAxis, yAxis);
         setAnimated(false);
         xAxis.setAnimated(false);
         yAxis.setAnimated(false);
     }

     /**
      * Construct a new CandleStickChart with the given axis and data.
      *
      * @param xAxis The x axis to use
      * @param yAxis The y axis to use
      * @param data The data to use, this is the actual list used so any changes to it will be reflected in the chart
      */
     public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis, ObservableList<Series<String, Number>> data) {
         this(xAxis, yAxis);
         setData(data);
     }

     // -------------- METHODS ------------------------------------------------------------------------------------------
     /** Called to update and layout the content for the plot
      *     更新布局*/
     @Override
     protected void layoutPlotChildren() {
         // we have nothing to layout if no data is present
         if (getData() == null) {
             return;
         }
         // update candle positions

         for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
             Series<String, Number> series = getData().get(seriesIndex);
             Iterator<Data<String, Number>> iter = getDisplayedDataIterator(series);
             Path seriesPath = null;
             if (series.getNode() instanceof Path) {
             	// System.out.println("clear path");
                 seriesPath = (Path) series.getNode();
                 seriesPath.getElements().clear();
             }
             while (iter.hasNext()) {
                 Data<String, Number> item = iter.next();
                 double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));//根据x的值得到绘图中的像素点横坐标
                 double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));//根据y的值得到绘图中的像素点纵坐标
                  // System.out.print("x: "+x+" y: "+y);
                 Node itemNode = item.getNode();
                 CandleStickExtraValues extra = (CandleStickExtraValues) item.getExtraValue();
                 if (itemNode instanceof Candle && extra != null) {
                 	  //System.out.println("iam a candle");
                     Candle candle = (Candle) itemNode;

                     double close = getYAxis().getDisplayPosition(extra.getClose());
                     double high = getYAxis().getDisplayPosition(extra.getHigh());
                     double low = getYAxis().getDisplayPosition(extra.getLow());

                     // calculate candle width
                     double candleWidth = -1;
                     if (getXAxis() instanceof CategoryAxis) {
                     	//System.out.println("get in");
                         CategoryAxis xa = (CategoryAxis) getXAxis();
                     //    candleWidth = xa.getDisplayPosition(xa.getTickUnit()) ; // use 90% width between ticks
                        // System.out.println("candleWidth: "+candleWidth);
                     }
                     // update candle
                     candle.update(close - y, high - y, low - y, candleWidth);
                     candle.updateTooltip(item.getYValue().doubleValue(), extra.getClose(), extra.getHigh(), extra.getLow());

                     // position the candle
                     candle.setLayoutX(x);
                     candle.setLayoutY(y);
                 }
                 //平均值的折线的点在这里添加
                 if (seriesPath != null) {
                     if (seriesPath.getElements().isEmpty()) {
                         seriesPath.getElements().add(new MoveTo(x, getYAxis().getDisplayPosition(extra.getAverage())));
                     } else {
                         seriesPath.getElements().add(new LineTo(x, getYAxis().getDisplayPosition(extra.getAverage())));
                     }
                 }
             }
         }
     }

     @Override protected void dataItemChanged(Data<String, Number> item) {

     }

     @Override protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
         Node candle = createCandle(getData().indexOf(series), item, itemIndex);
         if (shouldAnimate()) {
             candle.setOpacity(0);//设置不透明
             getPlotChildren().add(candle);
             // fade in new candle
             FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
             ft.setToValue(1);
             ft.play();
         } else {
             getPlotChildren().add(candle);
         }
         // always draw average line on top
         if (series.getNode() != null) {
             series.getNode().toFront();
         }
     }

     @Override protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
         final Node candle = item.getNode();
         if (shouldAnimate()) {
             // fade out old candle
             FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
             ft.setToValue(0);
             ft.setOnFinished(new EventHandler<ActionEvent>() {

                 @Override
                 public void handle(ActionEvent actionEvent) {
                     getPlotChildren().remove(candle);
                 }
             });
             ft.play();
         } else {
             getPlotChildren().remove(candle);
         }
     }

     @Override protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
         // handle any data already in series
         for (int j = 0; j < series.getData().size(); j++) {
             Data item = series.getData().get(j);
             Node candle = createCandle(seriesIndex, item, j);
             if (shouldAnimate()) {
                 candle.setOpacity(0);
                 getPlotChildren().add(candle);
                 // fade in new candle
                 FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                 ft.setToValue(1);
                 ft.play();
             } else {
                 getPlotChildren().add(candle);
             }
         }
         // create series path
         Path seriesPath = new Path();
         seriesPath.getStyleClass().setAll("candlestick-average-line", "series" + seriesIndex);
         series.setNode(seriesPath);
         getPlotChildren().add(seriesPath);
     }

     @Override protected void seriesRemoved(Series<String, Number> series) {
         // remove all candle nodes
         for (XYChart.Data<String, Number> d : series.getData()) {
             final Node candle = d.getNode();
             if (shouldAnimate()) {
                 // fade out old candle
                 FadeTransition ft = new FadeTransition(Duration.millis(500), candle);
                 ft.setToValue(0);
                 ft.setOnFinished(new EventHandler<ActionEvent>() {

                     @Override
                     public void handle(ActionEvent actionEvent) {
                         getPlotChildren().remove(candle);
                     }
                 });
                 ft.play();
             } else {
                 getPlotChildren().remove(candle);
             }
         }
     }

     /**
      * Create a new Candle node to represent a single data item
      *
      * @param seriesIndex The index of the series the data item is in
      * @param item        The data item to create node for
      * @param itemIndex   The index of the data item in the series
      * @return New candle node to represent the give data item
      */
     private Node createCandle(int seriesIndex, final Data item, int itemIndex) {
         Node candle = item.getNode();
         // check if candle has already been created
         if (candle instanceof Candle) {
             ((Candle) candle).setSeriesAndDataStyleClasses("series" + seriesIndex, "data" + itemIndex);
         } else {
             candle = new Candle("series" + seriesIndex, "data" + itemIndex);
             item.setNode(candle);
         }
         return candle;
     }

     /**
      * This is called when the range has been invalidated and we need to update it. If the axis are auto
      * ranging then we compile a list of all data that the given axis has to plot and call invalidateRange() on the
      * axis passing it that data.
      */
     @Override
     protected void updateAxisRange() {
         // For candle stick chart we need to override this method as we need to let the axis know that they need to be able
         // to cover the whole area occupied by the high to low range not just its center data value
         final Axis<String> xa = getXAxis();
         final Axis<Number> ya = getYAxis();
         List<String> xData = null;
         List<Number> yData = null;
         if (xa.isAutoRanging()) {
             xData = new ArrayList<String>();
         }
         if (ya.isAutoRanging()) {
             yData = new ArrayList<Number>();
         }
         if (xData != null || yData != null) {
             for (Series<String, Number> series : getData()) {
                 for (Data<String, Number> data : series.getData()) {
                     if (xData != null) {
                         xData.add(data.getXValue());
                     }
                     if (yData != null) {
                         CandleStickExtraValues extras = (CandleStickExtraValues) data.getExtraValue();
                         if (extras != null) {
                             yData.add(extras.getHigh());
                             yData.add(extras.getLow());
                         } else {
                             yData.add(data.getYValue());
                         }
                     }
                 }
             }
             if (xData != null) {
                 xa.invalidateRange(xData);
             }
             if (yData != null) {
                 ya.invalidateRange(yData);
             }
         }
     }
 }