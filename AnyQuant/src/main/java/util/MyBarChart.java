package util;

import java.util.Map;
import java.util.Map.Entry;

import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MyBarChart {

	private BarChart<String, Number> barchart;
	private Map<String , Integer>  data;

	public MyBarChart() {
		init();
	}


	public void addData(Series series) {
		barchart.getData().add(series);
	}

	public void addData(BarChart bc,Map<String , Integer>  data){
		bc.getData().clear();
		XYChart.Series series = new XYChart.Series();
		for(Entry<String, Integer> entry:data.entrySet()){
        	series.getData().add(new XYChart.Data(entry.getKey(),entry.getValue()));
        }
		bc.getData().add(series);
	}

	public BarChart<String, Number> createBarChart(Map<String , Integer>  data){
		this.data=data;
//		this.barchart.setAnimated(true);
		init();
		return this.barchart;
	}

	private void init() {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		barchart = new BarChart<String, Number>(xAxis, yAxis){/* (non-Javadoc)
		 * @see javafx.scene.chart.Chart#layoutChildren()
		 */
		@Override
		protected void layoutChildren() {
			super.layoutChildren();
		}};

		barchart.setOnMouseMoved(
		new EventHandler<MouseEvent>(){
            @Override public void handle(MouseEvent e){
//              System.out.println(e.getX());
            	String x_=xAxis.getValueForDisplay(e.getX());
            	barchart.getData().get(0);
//            	for(XYChart.Data t:barchart.getData()){

//            	}
//            	System.out.println(barchart.getData().);
            }
          });

	}

	public BarChart getBarChart() {
		return barchart;
	}

}

