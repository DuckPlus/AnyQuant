package ui.controller.candleStick;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import blimpl.BusinessFactory;
import blservice.OptionalStockBLService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
*
*@author:dsn
*@date:Apr 9, 2016
*/
public class DistributionChart {

	public DistributionChart(){
		init();
	}

	/**
	 *
	 */
	private void init() {
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		Iterator<Map.Entry<String, Integer>> itr_region=ctr.getRegionDistribution();
		while(itr_region.hasNext()){
			Entry<String,Integer> temp=itr_region.next();
			System.out.println();
			data.add(new PieChart.Data(temp.getKey(),temp.getValue()));
		}


		piechart = new PieChart(data);
		piechart.setLabelLineLength(10);
		piechart.setLegendSide(Side.RIGHT);
		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 24 arial;");
		for (final PieChart.Data dt : piechart.getData()) {
		   //TODO
		}
	}




	public PieChart getPiechart() {
		return piechart;
	}




	private OptionalStockBLService ctr=BusinessFactory.getOptionalBLService();
	private PieChart piechart;
}
